package com.jerry.eup2p.parts.p2p;

import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.core.AppEng;
import appeng.items.parts.PartModels;
import appeng.parts.p2p.CapabilityP2PTunnelPart;
import appeng.parts.p2p.P2PModels;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.jerry.eup2p.config.EUP2PConfig;
import net.minecraft.core.Direction;

import java.util.List;

public class EUP2PTunnelPart extends CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer> {
    private static final P2PModels MODELS = new P2PModels(AppEng.makeId("part/p2p/p2p_tunnel_eu"));

    private static final IEnergyContainer NULL_ENERGY_STORAGE = new NullEnergyStorage();

    @PartModels
    public static List<IPartModel> getModels() {
        return MODELS.getModels();
    }

    public EUP2PTunnelPart(IPartItem<?> partItem) {
        super(partItem, GTCapability.CAPABILITY_ENERGY_CONTAINER);
        inputHandler = new InputEnergyContainer();
        outputHandler = new OutputEnergyContainer();
        emptyHandler = NULL_ENERGY_STORAGE;
    }

    @Override
    public IPartModel getStaticModels() {
        return MODELS.getModel(this.isPowered(), this.isActive());
    }

    private class InputEnergyContainer implements IEnergyContainer {

        @Override
        public long acceptEnergyFromNetwork(Direction side, long voltage, long amperage) {
            long total = 0;
            //获取输出端个数
            final int outputTunnels = EUP2PTunnelPart.this.getOutputs().size();
            //如果输出端个数和电流都不为0则为false
            if (outputTunnels == 0 | amperage == 0) {
                return 0;
            }
            if (EUP2PConfig.NEED_BALANCE.get() && amperage % outputTunnels != 0) {
                return 0;
            }
            //每个输出端的电流
            final long amperagePerOutput = amperage / outputTunnels;
            long overflow = amperagePerOutput == 0 ? amperage : amperage % amperagePerOutput;

            for (EUP2PTunnelPart target : EUP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard capabilityGuard = target.getAdjacentCapability()) {
                    final IEnergyContainer output = capabilityGuard.get();
                    final long toSend = amperagePerOutput + overflow;
                    final long received = output.acceptEnergyFromNetwork(side, voltage, toSend);

                    overflow = toSend - received;
                    total += received;
                }
            }
            return total;
        }

        @Override
        public boolean inputsEnergy(Direction side) {
            return true;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            int total = 0;

            for (EUP2PTunnelPart t : EUP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard capabilityGuard = t.getAdjacentCapability()) {
                    total += capabilityGuard.get().changeEnergy(differenceAmount);
                }
            }

            return total;
        }

        @Override
        public long getEnergyStored() {
            int total = 0;

            for (EUP2PTunnelPart t : EUP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard capabilityGuard = t.getAdjacentCapability()) {
                    total += capabilityGuard.get().getEnergyStored();
                }
            }

            return total;
        }

        @Override
        public long getEnergyCapacity() {
            int total = 0;

            for (EUP2PTunnelPart t : EUP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard capabilityGuard = t.getAdjacentCapability()) {
                    total += capabilityGuard.get().getEnergyCapacity();
                }
            }

            return total;
        }

        @Override
        public long getInputAmperage() {
            int total = 0;

            for (EUP2PTunnelPart t : EUP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard capabilityGuard = t.getAdjacentCapability()) {
                    total += capabilityGuard.get().getInputAmperage();
                }
            }

            return total;
        }

        @Override
        public long getInputVoltage() {
            int total = 0;

            for (EUP2PTunnelPart t : EUP2PTunnelPart.this.getOutputs()) {
                try (CapabilityGuard capabilityGuard = t.getAdjacentCapability()) {
                    total += capabilityGuard.get().getInputVoltage();
                }
            }

            return total;
        }
    }

    private class OutputEnergyContainer implements IEnergyContainer {

        @Override
        public long acceptEnergyFromNetwork(Direction side, long voltage, long amperage) {
            try (CapabilityGuard input = getInputCapability()) {
                return input.get().acceptEnergyFromNetwork(side, voltage, amperage);
            }
        }

        @Override
        public boolean inputsEnergy(Direction side) {
            return false;
        }

        @Override
        public boolean outputsEnergy(Direction side) {
            return true;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            try (CapabilityGuard input = getInputCapability()) {
                return input.get().changeEnergy(differenceAmount);
            }
        }

        @Override
        public long getEnergyStored() {
            try (CapabilityGuard input = getInputCapability()) {
                return input.get().getEnergyStored();
            }
        }

        @Override
        public long getEnergyCapacity() {
            try (CapabilityGuard input = getInputCapability()) {
                return input.get().getEnergyStored();
            }
        }

        @Override
        public long getInputAmperage() {
            try (CapabilityGuard input = getInputCapability()) {
                return input.get().getEnergyStored();
            }
        }

        @Override
        public long getInputVoltage() {
            try (CapabilityGuard input = getInputCapability()) {
                return input.get().getEnergyStored();
            }
        }
    }

    private static class NullEnergyStorage implements IEnergyContainer {

        @Override
        public long acceptEnergyFromNetwork(Direction side, long voltage, long amperage) {
            return 0;
        }

        @Override
        public boolean inputsEnergy(Direction side) {
            return false;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            return 0;
        }

        @Override
        public long getEnergyStored() {
            return 0;
        }

        @Override
        public long getEnergyCapacity() {
            return 0;
        }

        @Override
        public long getInputAmperage() {
            return 0;
        }

        @Override
        public long getInputVoltage() {
            return 0;
        }
    }
}
