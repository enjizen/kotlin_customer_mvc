package com.wanchalerm.tua.customer.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch


@Aspect
@Component
class LoggingAspect {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Around("execution(* com.wanchalerm.tua.customer.service..*(..)))")
    @Throws(Throwable::class)
    fun profileAllMethods(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        //Measure method execution time
        stopWatch.start()
        val result = proceedingJoinPoint.proceed()
        stopWatch.stop()
        (proceedingJoinPoint.signature as MethodSignature).let {
            //Log method execution time
            logger.info("Execution time of ${it.declaringType.simpleName}.${it.name} :: ${stopWatch.totalTimeMillis} ms")
        }

        return result
    }

}