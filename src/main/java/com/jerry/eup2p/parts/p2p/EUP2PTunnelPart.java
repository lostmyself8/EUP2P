package com.jerry.eup2p.parts.p2p;

import appeng.api.config.PowerUnit;
import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.core.AppEng;
import appeng.items.parts.PartModels;
import appeng.parts.p2p.CapabilityP2PTunnelPart;
import appeng.parts.p2p.P2PModels;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.config.ConfigHolder;
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

        private InputEnergyContainer() {

        }

        @Override
        public long acceptEnergyFromNetwork(Direction direction, long voltage, long amperage) {
            long total = 0;
            // 获取输出端个数
            final int outputTunnels = EUP2PTunnelPart.this.getOutputs().size();
            // 如果输出端个数和电流都不为0则为false
            if (outputTunnels == 0 | amperage == 0) {
                return 0;
            } else {
                // 每个输出端的电流
                final long amperagePerOutput = amperage / outputTunnels;
                long overflow = amperagePerOutput == 0 ? amperage : amperage % amperagePerOutput;

                for (EUP2PTunnelPart target : EUP2PTunnelPart.this.getOutputs()) {
                    CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard capabilityGuard = target.getAdjacentCapability();

                    try {
                        final IEnergyContainer output = capabilityGuard.get();
                        final long toSend = amperagePerOutput + overflow;
//                        if (toSend == 0) break;
                        final long received = output.acceptEnergyFromNetwork(target.getSide().getOpposite(), voltage, toSend);

                        overflow = toSend - received;
                        total += received;
                    } catch (Throwable throwable_1) {
                        if (capabilityGuard != null) {
                            try {
                                capabilityGuard.close();
                            } catch (Throwable throwable_2) {
                                throwable_1.addSuppressed(throwable_2);
                            }
                            throw throwable_1;
                        }
                    }
                    if (capabilityGuard != null) {
                        capabilityGuard.close();
                    }
                }
                if (total > 0) {
                    EUP2PTunnelPart.this.queueTunnelDrain(PowerUnit.FE, (double) total * voltage * ConfigHolder.INSTANCE.compat.energy.euToFeRatio);
                }
                return total;
            }
        }

        @Override
        public boolean inputsEnergy(Direction direction) {
            return EUP2PTunnelPart.this.getSide() == direction;
        }

        @Override
        public long changeEnergy(long l) {
            return 0;
        }

        @Override
        public long getEnergyStored() {
            long total = 0;

            for (EUP2PTunnelPart target : EUP2PTunnelPart.this.getOutputs()) {
                CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard capabilityGuard = target.getAdjacentCapability();

                try {
                    total = Math.addExact(total, capabilityGuard.get().getEnergyStored());
                } catch (Throwable throwable_1) {

                    if (throwable_1 instanceof ArithmeticException) {
                        if (capabilityGuard != null) {
                            capabilityGuard.close();
                        }
                        return 0;
                    }

                    if (capabilityGuard != null) {
                        try {
                            capabilityGuard.close();
                        } catch (Throwable throwable_2) {
                            throwable_1.addSuppressed(throwable_2);
                        }
                    }
                    throw throwable_1;
                }
                if (capabilityGuard != null) {
                    capabilityGuard.close();
                }
            }

            return total;
        }

        @Override
        public long getEnergyCapacity() {
            long total = 0;

            for (EUP2PTunnelPart target : EUP2PTunnelPart.this.getOutputs()) {
                CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard capabilityGuard = target.getAdjacentCapability();
                try {
                    total = Math.addExact(total, capabilityGuard.get().getEnergyCapacity());
                } catch (Throwable throwable_1) {
                    if (throwable_1 instanceof ArithmeticException) {
                        capabilityGuard.close();
                        // 如果是计算错误异常，超过了long那就返回long
                        return Long.MAX_VALUE;
                    }

                    if (capabilityGuard != null) {
                        try {
                            capabilityGuard.close();
                        } catch (Throwable throwable_2) {
                            throwable_1.addSuppressed(throwable_2);
                        }
                    }
                    throw throwable_1;
                }
                if (capabilityGuard != null) {
                    capabilityGuard.close();
                }
            }

            return total;
        }

        @Override
        public long getInputAmperage() {
            long total = 0;

            for (EUP2PTunnelPart target : EUP2PTunnelPart.this.getOutputs()) {
                CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard capabilityGuard = target.getAdjacentCapability();
                try {
                    total = Math.addExact(total, capabilityGuard.get().getInputAmperage());
                } catch (Throwable throwable_1) {
                    if (throwable_1 instanceof ArithmeticException) {
                        capabilityGuard.close();
                        // 如果是计算错误异常，超过了long那就返回long
                        return Long.MAX_VALUE;
                    }

                    if (capabilityGuard != null) {
                        try {
                            capabilityGuard.close();
                        } catch (Throwable throwable_2) {
                            throwable_1.addSuppressed(throwable_2);
                        }
                    }
                    throw throwable_1;
                }
                if (capabilityGuard != null) {
                    capabilityGuard.close();
                }
            }

            return total;
        }

        @Override
        public long getInputVoltage() {
            long total = 0;

            for (EUP2PTunnelPart target : EUP2PTunnelPart.this.getOutputs()) {
                CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard capabilityGuard = target.getAdjacentCapability();
                try {
                    // 返回电压最大的一个端口的电压
                    total = Math.max(total, capabilityGuard.get().getInputVoltage());
                    // 返回最远的端口所连接的机器或者线缆的电压
//                    return capabilityGuard.get().getInputVoltage();
                } catch (Throwable throwable_1) {
                    if (capabilityGuard != null) {
                        try {
                            capabilityGuard.close();
                        } catch (Throwable throwable_2) {
                            throwable_1.addSuppressed(throwable_2);
                        }
                    }
                    throw throwable_1;
                }
                if (capabilityGuard != null) {
                    capabilityGuard.close();
                }
            }

            return total;
        }
    }

    private class OutputEnergyContainer implements IEnergyContainer {

        private OutputEnergyContainer() {

        }

        @Override
        public long acceptEnergyFromNetwork(Direction direction, long voltage, long amperage) {
            return 0;
        }

        @Override
        public boolean inputsEnergy(Direction direction) {
            return false;
        }

        @Override
        public boolean outputsEnergy(Direction direction) {
            return EUP2PTunnelPart.this.getSide() == direction;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            return 0;
        }

        @Override
        public long getEnergyStored() {
            CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard input = EUP2PTunnelPart.this.getInputCapability();

            long stored;
            try {
                stored = input.get().getEnergyStored();
            } catch (Throwable throwable_1) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable throwable_2) {
                        throwable_1.addSuppressed(throwable_2);
                    }
                }
                throw throwable_1;
            }
            if (input != null) {
                input.close();
            }

            return stored;
        }

        @Override
        public long getEnergyCapacity() {
            CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard input = EUP2PTunnelPart.this.getInputCapability();

            long capacity;
            try {
                capacity = input.get().getEnergyCapacity();
            } catch (Throwable throwable_1) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable throwable_2) {
                        throwable_1.addSuppressed(throwable_2);
                    }
                }
                throw throwable_1;
            }
            if (input != null) {
                input.close();
            }

            return capacity;
        }

        @Override
        public long getInputAmperage() {
            return 0;
        }

        @Override
        public long getOutputAmperage() {
            CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard input = EUP2PTunnelPart.this.getInputCapability();

            long amperage;
            try {
                amperage = input.get().getOutputAmperage();
            } catch (Throwable throwable_1) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable throwable_2) {
                        throwable_1.addSuppressed(throwable_2);
                    }
                }
                throw throwable_1;
            }
            if (input != null) {
                input.close();
            }

            return amperage;
        }

        @Override
        public long getInputVoltage() {
            return 0;
        }

        @Override
        public long getOutputVoltage() {
            CapabilityP2PTunnelPart<EUP2PTunnelPart, IEnergyContainer>.CapabilityGuard input = EUP2PTunnelPart.this.getInputCapability();

            long voltage;
            try {
                voltage = input.get().getOutputVoltage();
            } catch (Throwable throwable_1) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable throwable_2) {
                        throwable_1.addSuppressed(throwable_2);
                    }
                }
                throw throwable_1;
            }
            if (input != null) {
                input.close();
            }

            return voltage;
        }
    }

    private static class NullEnergyStorage implements IEnergyContainer {

        private NullEnergyStorage() {

        }

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
