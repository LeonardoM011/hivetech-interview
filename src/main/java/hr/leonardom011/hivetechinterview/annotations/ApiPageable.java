package hr.leonardom011.hivetechinterview.annotations;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
        @Parameter(name = "page", schema = @Schema(type = "integer"), in = ParameterIn.QUERY, description = "Results page you want to retrieve (0..N)"),
        @Parameter(name = "size", schema = @Schema(type = "integer"), in = ParameterIn.QUERY, description = "Number of records per page."),
        @Parameter(name = "sort", array = @ArraySchema(maxItems = 5, uniqueItems = true), schema = @Schema(type = "string"), in = ParameterIn.QUERY, description = "Sorting criteria in the format: ex. &sort=property,asc&sort=property,desc."
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
public @interface ApiPageable {}
