package com.cargarantie.idoit.api.demo;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectBrowser;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CustomCategory extends IdoitCategory {

  private String simpleString; //Field types: TextField, HTML-Editor, Description
  private Boolean yesNoField; //TODO: json conversion
  private Dialog dialogField; //set a value by id, constant or text
  private LocalDateTime dateField; //TODO: json conversion. Json input is a complex object
  private ObjectBrowser objectReference; //
}
