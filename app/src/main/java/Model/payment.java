package Model;


import org.json.JSONObject;

import java.util.ArrayList;

public class payment {
 private String id;
 private String entity;
 private float amount;
 private String currency;
 private String status;
 private String order_id = null;
 private String invoice_id = null;
 private boolean international;
 private String method;
 private float amount_refunded;
 private String refund_status = null;
 private boolean captured;
 private String description = null;
 private String card_id;
 private String bank = null;
 private String wallet = null;
 private String vpa = null;
 private String email;
 private String contact;
 ArrayList<JSONObject> notes = new ArrayList < JSONObject > ();
 private String fee = null;
 private String tax = null;
 private String error_code = null;
 private String error_description = null;
 private String emi = null;
 private float created_at;

 public payment(String order_id, float amount, String method, float created_at) {

  this.amount = amount;
  this.order_id = order_id;
  this.method = method;
  this.created_at = created_at;
 }

// Getter Methods

 public String getId() {
  return id;
 }

 public String getEntity() {
  return entity;
 }

 public float getAmount() {
  return amount;
 }

 public String getCurrency() {
  return currency;
 }

 public String getStatus() {
  return status;
 }

 public String getOrder_id() {
  return order_id;
 }

 public String getInvoice_id() {
  return invoice_id;
 }

 public boolean getInternational() {
  return international;
 }

 public String getMethod() {
  return method;
 }

 public float getAmount_refunded() {
  return amount_refunded;
 }

 public String getRefund_status() {
  return refund_status;
 }

 public boolean getCaptured() {
  return captured;
 }

 public String getDescription() {
  return description;
 }

 public String getCard_id() {
  return card_id;
 }

 public String getBank() {
  return bank;
 }

 public String getWallet() {
  return wallet;
 }

 public String getVpa() {
  return vpa;
 }

 public String getEmail() {
  return email;
 }

 public String getContact() {
  return contact;
 }

 public String getFee() {
  return fee;
 }

 public String getTax() {
  return tax;
 }

 public String getError_code() {
  return error_code;
 }

 public String getError_description() {
  return error_description;
 }

 public String getEmi() {
  return emi;
 }

 public float getCreated_at() {
  return created_at;
 }

 // Setter Methods 

 public void setId(String id) {
  this.id = id;
 }

 public void setEntity(String entity) {
  this.entity = entity;
 }

 public void setAmount(float amount) {
  this.amount = amount;
 }

 public void setCurrency(String currency) {
  this.currency = currency;
 }

 public void setStatus(String status) {
  this.status = status;
 }

 public void setOrder_id(String order_id) {
  this.order_id = order_id;
 }

 public void setInvoice_id(String invoice_id) {
  this.invoice_id = invoice_id;
 }

 public void setInternational(boolean international) {
  this.international = international;
 }

 public void setMethod(String method) {
  this.method = method;
 }

 public void setAmount_refunded(float amount_refunded) {
  this.amount_refunded = amount_refunded;
 }

 public void setRefund_status(String refund_status) {
  this.refund_status = refund_status;
 }

 public void setCaptured(boolean captured) {
  this.captured = captured;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public void setCard_id(String card_id) {
  this.card_id = card_id;
 }

 public void setBank(String bank) {
  this.bank = bank;
 }

 public void setWallet(String wallet) {
  this.wallet = wallet;
 }

 public void setVpa(String vpa) {
  this.vpa = vpa;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public void setContact(String contact) {
  this.contact = contact;
 }

 public void setFee(String fee) {
  this.fee = fee;
 }

 public void setTax(String tax) {
  this.tax = tax;
 }

 public void setError_code(String error_code) {
  this.error_code = error_code;
 }

 public void setError_description(String error_description) {
  this.error_description = error_description;
 }

 public void setEmi(String emi) {
  this.emi = emi;
 }

 public void setCreated_at(float created_at) {
  this.created_at = created_at;
 }
}
