package Model;
// import payment.java

import java.util.ArrayList;

public class paymentList {
 private String entity;
 private float count;
 ArrayList< payment > payments = new ArrayList < payment > ();


 // Getter Methods 

 public String getEntity() {
  return entity;
 }

 public float getCount() {
  return count;
 }

 // Setter Methods 

 public void setEntity(String entity) {
  this.entity = entity;
 }

 public void setCount(float count) {
  this.count = count;
 }
}
