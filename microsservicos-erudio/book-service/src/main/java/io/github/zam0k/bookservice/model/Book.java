package io.github.zam0k.bookservice.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

@Entity(name = "book")
public class Book {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "author", nullable = false, length = 180)
  private String author;

  @Column(name = "launch_date", nullable = false)
  @Temporal(DATE)
  private Date launchDate;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false, length = 250)
  private String title;

  @Transient private String currency;
  @Transient private String environment;

  public Book() {}

  public Book(
      Long id,
      String author,
      String title,
      Date launchDate,
      BigDecimal price,
      String currency,
      String environment) {
    this.id = id;
    this.author = author;
    this.launchDate = launchDate;
    this.price = price;
    this.title = title;
    this.currency = currency;
    this.environment = environment;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getLaunchDate() {
    return launchDate;
  }

  public void setLaunchDate(Date launchDate) {
    this.launchDate = launchDate;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(getId(), book.getId())
        && Objects.equals(getAuthor(), book.getAuthor())
        && Objects.equals(getLaunchDate(), book.getLaunchDate())
        && Objects.equals(getPrice(), book.getPrice())
        && Objects.equals(getTitle(), book.getTitle())
        && Objects.equals(getCurrency(), book.getCurrency())
        && Objects.equals(getEnvironment(), book.getEnvironment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getAuthor(),
        getLaunchDate(),
        getPrice(),
        getTitle(),
        getCurrency(),
        getEnvironment());
  }
}
