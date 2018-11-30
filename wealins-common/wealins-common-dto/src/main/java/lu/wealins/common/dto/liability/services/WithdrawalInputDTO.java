
package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.List;

public class WithdrawalInputDTO {

    protected WithdrawalInputDTO.ImpPasPolicyAgentShares impPasPolicyAgentShares;

    protected WithdrawalInputDTO.ImpPocPolicyCoverages impPocPolicyCoverages;

    protected WithdrawalInputDTO.ImpGrpPfi impGrpPfi;

    public WithdrawalInputDTO.ImpPasPolicyAgentShares getImpPasPolicyAgentShares() {
        return impPasPolicyAgentShares;
    }

    public void setImpPasPolicyAgentShares(WithdrawalInputDTO.ImpPasPolicyAgentShares value) {
        this.impPasPolicyAgentShares = value;
    }


    public WithdrawalInputDTO.ImpPocPolicyCoverages getImpPocPolicyCoverages() {
        return impPocPolicyCoverages;
    }


    public void setImpPocPolicyCoverages(WithdrawalInputDTO.ImpPocPolicyCoverages value) {
        this.impPocPolicyCoverages = value;
    }


    public WithdrawalInputDTO.ImpGrpPfi getImpGrpPfi() {
        return impGrpPfi;
    }


    public void setImpGrpPfi(WithdrawalInputDTO.ImpGrpPfi value) {
        this.impGrpPfi = value;
    }

    public static class ImpGrpPfi {

        protected List<WithdrawalInputDTO.ImpGrpPfi.Row> rows;

        public List<WithdrawalInputDTO.ImpGrpPfi.Row> getRows() {
            if (rows == null) {
                rows = new ArrayList<WithdrawalInputDTO.ImpGrpPfi.Row>();
            }
            return this.rows;
        }

        public static class Row {

			protected WithdrawalInputDTO.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions impItmPfiPolicyFundInstructions;
            protected WithdrawalInputDTO.ImpGrpPfi.Row.ItmSelPfiIefSupplied itmSelPfiIefSupplied;


            public WithdrawalInputDTO.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions getImpItmPfiPolicyFundInstructions() {
                return impItmPfiPolicyFundInstructions;
            }


            public void setImpItmPfiPolicyFundInstructions(WithdrawalInputDTO.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions value) {
                this.impItmPfiPolicyFundInstructions = value;
            }


            public WithdrawalInputDTO.ImpGrpPfi.Row.ItmSelPfiIefSupplied getItmSelPfiIefSupplied() {
                return itmSelPfiIefSupplied;
            }


            public void setItmSelPfiIefSupplied(WithdrawalInputDTO.ImpGrpPfi.Row.ItmSelPfiIefSupplied value) {
                this.itmSelPfiIefSupplied = value;
            }

            public static class ImpItmPfiPolicyFundInstructions {

                protected String policy;

                protected short holding;

                protected short type;

                protected String fund;

                protected short movementType;

                protected String movementValue;

                protected String movementCcy;

                protected String switchFee;

                protected String switchFeeCcy;

                protected String instructionDate;


                public String getPolicy() {
                    return policy;
                }


                public void setPolicy(String value) {
                    this.policy = value;
                }


                public short getHolding() {
                    return holding;
                }


                public void setHolding(short value) {
                    this.holding = value;
                }


                public short getType() {
                    return type;
                }


                public void setType(short value) {
                    this.type = value;
                }


                public String getFund() {
                    return fund;
                }


                public void setFund(String value) {
                    this.fund = value;
                }


                public short getMovementType() {
                    return movementType;
                }


                public void setMovementType(short value) {
                    this.movementType = value;
                }


                public String getMovementValue() {
                    return movementValue;
                }


                public void setMovementValue(String value) {
                    this.movementValue = value;
                }


                public String getMovementCcy() {
                    return movementCcy;
                }


                public void setMovementCcy(String value) {
                    this.movementCcy = value;
                }


                public String getSwitchFee() {
                    return switchFee;
                }


                public void setSwitchFee(String value) {
                    this.switchFee = value;
                }


                public String getSwitchFeeCcy() {
                    return switchFeeCcy;
                }


                public void setSwitchFeeCcy(String value) {
                    this.switchFeeCcy = value;
                }


                public String getInstructionDate() {
                    return instructionDate;
                }


                public void setInstructionDate(String value) {
                    this.instructionDate = value;
                }

            }


            public static class ItmSelPfiIefSupplied {

                protected String selectChar;


                public String getSelectChar() {
                    return selectChar;
                }


                public void setSelectChar(String value) {
                    this.selectChar = value;
                }

            }

        }

    }

    public static class ImpPasPolicyAgentShares {

        protected String agent;
        protected String percentage;


        public String getAgent() {
            return agent;
        }


        public void setAgent(String value) {
            this.agent = value;
        }


        public String getPercentage() {
            return percentage;
        }


        public void setPercentage(String value) {
            this.percentage = value;
        }

    }

    public static class ImpPocPolicyCoverages {

        protected String policy;
        protected short coverage;


        public String getPolicy() {
            return policy;
        }


        public void setPolicy(String value) {
            this.policy = value;
        }


        public short getCoverage() {
            return coverage;
        }


        public void setCoverage(short value) {
            this.coverage = value;
        }

    }

}
