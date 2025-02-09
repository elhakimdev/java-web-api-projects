package com.sass.erp.finance.cash.api_service.models.entities;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
public class EntityManagerStrategy {

  public enum EntityOperation {
    CREATE,
    UPDATE
  }

  public record FieldContext(Field field, Object entity) {}

  private static final Map<EntityOperation, BiConsumer<FieldContext, Object>> EntityOperationMap = Map.of(
    EntityOperation.CREATE, (ctx, value) -> setField(ctx.field(), ctx.entity(), value),
    EntityOperation.UPDATE, (ctx, value) -> { if (value != null) setField(ctx.field(), ctx.entity(), value); }
  );

  public static void applyOperation(EntityOperation operation, FieldContext context, Object value) {
    EntityOperationMap.get(operation).accept(context, value);
  }

  private static void setField(Field field, Object entity, Object value) {
    try {
      field.set(entity, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
