package com.dev.aalto.paycraft.audit;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl") /* All this to handle @ModifiedBy */
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    @NotNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of("PAYCRAFT");
    }
}