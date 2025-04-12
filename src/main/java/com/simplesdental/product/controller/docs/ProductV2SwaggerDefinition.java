package com.simplesdental.product.controller.docs;

import com.simplesdental.product.model.Product.ProductV2;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.simplesdental.product.Utils.ProductV2Examples.*;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Product API", version = "v2", description = "API for managing products"))
public interface ProductV2SwaggerDefinition {

    @Operation(summary = "List of all Products (v2)", description = "Return a pageable list of all products and it's categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductV2.class),
                            examples = @ExampleObject(name = "Pageable response example", value = PAGE_RETURN_EXAMPLE)))
    })
    @GetMapping
    Page<ProductV2> getAllProducts(@ParameterObject Pageable pageable);

    @Operation(summary = "Get product by ID", description = "Retrieves a single product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"   ,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductV2.class),
                            examples = @ExampleObject(name = "Return single Product", value = RETURN_SINGLE_PRODUCT_EXAMPLE))),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @GetMapping("/{id}")
    ResponseEntity<ProductV2> getProductById(@PathVariable Long id);

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductV2.class),
                            examples = @ExampleObject(name = "Product created", value = RETURN_SINGLE_PRODUCT_EXAMPLE))),
            @ApiResponse(responseCode = "500", content = @Content())
    })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product data to be created",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductV2.class),
                    examples = @ExampleObject(name = "New product", value = CREATE_UPDATE_EXAMPLE)))
    ProductV2 createProduct(@RequestBody ProductV2 productV2);

    @Operation(summary = "Update a product (v2)", description = "Update the existing product based on the ID passed in the parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductV2.class),
                            examples = @ExampleObject(name = "Updated product", value = RETURN_SINGLE_PRODUCT_EXAMPLE))),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PutMapping("/{id}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product data to be updated",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductV2.class),
                    examples = @ExampleObject(name = "Product data to be updated", value = CREATE_UPDATE_EXAMPLE)))
    ResponseEntity<ProductV2> updateProduct(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody ProductV2 productV2);

    @Operation(summary = "Delete a product (v2)", description = "Permanently remove a product from the database based on the passed id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id);
}