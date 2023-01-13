package com.shareNwork.domain;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailData {

    private String emailType;
    private String mailTo;
    private Map<String, Object> emailTemplateVariables;
}
