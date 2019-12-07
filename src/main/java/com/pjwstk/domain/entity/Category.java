package com.pjwstk.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamedQueries({
    @NamedQuery(
        name = "Category.findCategoryByName",
        query = "SELECT c FROM Category c WHERE c.name like :name"),
    @NamedQuery(
        name = "Category.getCategoryList",
        query = "SELECT c FROM Category c"),
    @NamedQuery(
        name = "Category.findCategoryById",
        query = "SELECT c FROM Category c WHERE c.id IN :ids"),
    @NamedQuery(
        name = "Category.getCategoryIds",
        query = "SELECT c.id FROM Category c")
})

@Entity
@Table(name = "Category", indexes = {@Index(name = "category_name", columnList = "name")})
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "Name", unique = true, length = 50)
  @NotNull
  private String name;

  @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Recipe> recipes = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }
}
