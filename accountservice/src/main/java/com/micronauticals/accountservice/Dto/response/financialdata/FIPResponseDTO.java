package com.micronauticals.accountservice.Dto.response.financialdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FIPResponseDTO {
    private List<Fip> fips;
    private String format;
    private String id;
    private DataRange dataRange;
    private String consentId;
    private String status;
    private String traceId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fip {
        private String fipID;
        private List<Account> accounts;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Account {
            private String linkRefNumber;
            private String maskedAccNumber;
            private AccountData data;
            private String FIstatus;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class AccountData {
                private AccountDetail account;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class AccountDetail {
                    private String linkedAccRef;
                    private String maskedAccNumber;
                    private String type;
                    private String version;
                    private Profile profile;
                    private Summary summary;
                    private Transactions transactions;

                    @Data
                    @Builder
                    @NoArgsConstructor
                    @AllArgsConstructor
                    public static class Profile {
                        private Holders holders;

                        @Data
                        @Builder
                        @NoArgsConstructor
                        @AllArgsConstructor
                        public static class Holders {
                            private String type;
                            private List<Holder> holder;

                            @Data
                            @Builder
                            @NoArgsConstructor
                            @AllArgsConstructor
                            public static class Holder {
                                private String address;
                                private String ckycCompliance;
                                private String dob;
                                private String email;
                                private String mobile;
                                private String name;
                                private String nominee;
                                private String pan;
                            }
                        }
                    }

                    @Data
                    @Builder
                    @NoArgsConstructor
                    @AllArgsConstructor
                    public static class Summary {
                        private String balanceDateTime;
                        private String branch;
                        private String currency;
                        private String currentBalance;
                        private String currentODLimit;
                        private String drawingLimit;
                        private String facility;
                        private String ifscCode;
                        private String micrCode;
                        private String openingDate;
                        private Pending pending;
                        private String status;
                        private String type;

                        @Data
                        @Builder
                        @NoArgsConstructor
                        @AllArgsConstructor
                        public static class Pending {
                            private String amount;
                            private String transactionType;
                        }
                    }

                    @Data
                    @Builder
                    @NoArgsConstructor
                    @AllArgsConstructor
                    public static class Transactions {
                        private String startDate;
                        private String endDate;
                        private List<Transaction> transaction;

                        @Data
                        @Builder
                        @NoArgsConstructor
                        @AllArgsConstructor
                        public static class Transaction {
                            private String amount;
                            private String currentBalance;
                            private String mode;
                            private String narration;
                            private String reference;
                            private String transactionTimestamp;
                            private String txnId;
                            private String type;
                            private String valueDate;
                        }
                    }
                }
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataRange {
        private String to;
        private String from;
    }
}
