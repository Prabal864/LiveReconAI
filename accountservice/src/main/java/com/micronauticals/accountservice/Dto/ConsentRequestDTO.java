package com.micronauticals.accountservice.Dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.util.List;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsentRequestDTO {
    private DataRange dataRange;
    private String vua;
    private String PAN;
    private ConsentDuration consentDuration;
    private String fetchType;
    private List<Context> context;
    private Frequency frequency;
    private List<DataFilter> dataFilter;
    private Purpose purpose;
    private List<String> consentTypes;
    private String redirectUrl;
    private List<String> fiTypes;
    private ConsentDateRange consentDateRange;
    private DataLife dataLife;
    private String consentMode;
    private AdditionalParams additionalParams;
    private boolean enableAdditionalPhoneNumber;

    @Data public static class DataRange {
        private String from;
        private String to;
    }

    @Data public static class ConsentDuration {
        private String unit;
        private int value;
    }

    @Data public static class Context {
        private String key;
        private String value;
    }

    @Data public static class Frequency {
        private String unit;
        private int value;
    }

    @Data public static class DataFilter {
        private String operator;
        private String type;
        private String value;
    }

    @Data
    public static class Purpose {
        private String refUri;  // move refUri here, not inside category
        private String code;
        private String text;
        private Category category;

        @Data
        public static class Category {
            private String type;  // only 'type' here, NO refUri
        }
    }


    @Data public static class ConsentDateRange {
        private String startDate;
        private String endDate;
    }

    @Data public static class DataLife {
        private String unit;
        private int value;
    }

    @Data
    public static class AdditionalParams {
        private List<String> tags;
    }

}
