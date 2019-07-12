package pl.kuba565.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private final String SERVICE_POINTCUT = "execution(* pl.kuba565.*.*.*(..))";

    @Around(SERVICE_POINTCUT)
    public Object logAdvice(ProceedingJoinPoint jp) throws Throwable {

        LOG.info("[METHOD] --------> {}", jp.getSignature().toShortString());

        Object[] signatureArgs = jp.getArgs();
        for (Object signatureArg : signatureArgs) {
            LOG.info("[ARGS] --------> {}: {}", signatureArg.getClass().getSimpleName(),
                    signatureArg.toString());
        }

        Instant startTime = Instant.now();
        Object obj = jp.proceed();
        Instant endTime = Instant.now();

        int nano = Duration.between(startTime, endTime).getNano();
        long milliseconds = TimeUnit.MILLISECONDS.convert(nano, TimeUnit.NANOSECONDS);
        LOG.info("[METRICS] --------------------> {}, time: {} {} ", jp.getSignature().toShortString(),
                milliseconds, "milliseconds");

        return obj;
    }
}