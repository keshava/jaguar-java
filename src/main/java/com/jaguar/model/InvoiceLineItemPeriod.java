// File generated from our OpenAPI spec
package com.jaguar.model;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class InvoiceLineItemPeriod extends JaguarObject {
  /** End of the line item's billing period. */
  @SerializedName("end")
  Long end;

  /** Start of the line item's billing period. */
  @SerializedName("start")
  Long start;
}
