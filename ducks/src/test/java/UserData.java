import java.util.Objects;

public class UserData {
  private String code;
  private String emailAddress;
  private String taxId;
  private String company;
  private String firstName;
  private String lastName;
  private String address1;
  private String address2;
  private String city;
  private String postCode;
  private String phone;
  private String mobilePhone;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "code='" + code + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", taxId='" + taxId + '\'' +
            ", company='" + company + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", address1='" + address1 + '\'' +
            ", address2='" + address2 + '\'' +
            ", city='" + city + '\'' +
            ", postCode='" + postCode + '\'' +
            ", phone='" + phone + '\'' +
            ", mobilePhone='" + mobilePhone + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return Objects.equals(code, userData.code) &&
            Objects.equals(emailAddress, userData.emailAddress) &&
            Objects.equals(taxId, userData.taxId) &&
            Objects.equals(company, userData.company) &&
            Objects.equals(firstName, userData.firstName) &&
            Objects.equals(lastName, userData.lastName) &&
            Objects.equals(address1, userData.address1) &&
            Objects.equals(address2, userData.address2) &&
            Objects.equals(city, userData.city) &&
            Objects.equals(postCode, userData.postCode) &&
            Objects.equals(phone, userData.phone) &&
            Objects.equals(mobilePhone, userData.mobilePhone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, emailAddress, taxId, company, firstName, lastName, address1, address2, city, postCode, phone, mobilePhone);
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }
}
