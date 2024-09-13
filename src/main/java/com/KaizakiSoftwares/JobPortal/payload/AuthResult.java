package com.KaizakiSoftwares.JobPortal.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@Setter
@ToString
public class AuthResult {
    Field.UUID id;
    boolean result;
}
