package org.folio.spring.tenant.controller;

import static org.folio.spring.web.utility.RequestHeaderUtility.unsupportedAccept;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.io.IOException;
import java.sql.SQLException;
import org.folio.spring.tenant.annotation.TenantHeader;
import org.folio.spring.tenant.hibernate.HibernateSchemaService;
import org.folio.spring.tenant.model.request.TenantAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_/tenant")
public class TenantController {

  private HibernateSchemaService hibernateSchemaService;

  /**
   * Initializer.
   *
   * @param hibernateSchemaService The hibernateSchemaService instance.
   */
  public TenantController(HibernateSchemaService hibernateSchemaService) {

    this.hibernateSchemaService = hibernateSchemaService;
  }

  @PostMapping
  public ResponseEntity<String> create(
    @TenantHeader String tenant,
    @RequestBody @Validated TenantAttributes attributes,
    @RequestHeader(required = false) String accept
  ) throws SQLException, IOException {
    if (unsupportedAccept(accept, MediaType.TEXT_PLAIN)) {
      return ResponseEntity.status(406).build();
    }

    hibernateSchemaService.createTenant(tenant);
    return new ResponseEntity<>("Success", CREATED);
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(
    @TenantHeader String tenant,
    @RequestHeader(required = false) String accept
  ) throws SQLException {
    if (unsupportedAccept(accept, MediaType.TEXT_PLAIN)) {
      return ResponseEntity.status(406).build();
    }

    hibernateSchemaService.deleteTenant(tenant);
    return new ResponseEntity<>(NO_CONTENT);
  }

}
