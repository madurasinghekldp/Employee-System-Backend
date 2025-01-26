package org.ems.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    private final String status = "Success";
    private Object data;
}
