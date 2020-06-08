package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.rest.Batch;
import com.cargarantie.idoit.api.rest.IdoitRequest;
import com.cargarantie.idoit.api.rest.NamedRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IdoitSession {
  public <T extends IdoitObject> void upsertObjects(Collection<T> currentObjects,
      Collection<T> updateObjects);

  public <T extends IdoitObject> List<T> readObjects(Class<T> objectClass);
  public <T extends IdoitObject> List<T> readObjects(Class<T> objectClass, Object filterSettings);
  <T> T send(IdoitRequest<T> request);
  <T> Map<String, T> send(Batch<T> requests);

  /*
  Info: idoit.addons
  idoit.constants
idoit.license
idoit.version

cmdb.category_info
object_type_categories
   */

  /*
  allgemeiner search: idoit.search
  cmdb.impact - relations zu einem Objekt. Verwendung unklar
   */

  /*
  console.commands.listCommands	Documentation follows
console.auth.cleanup	Documentation follows
console.document.compile	Documentation follows
console.dynamicgroups.sync	Documentation follows
console.import.csv	Documentation follows
console.ldap.sync	Documentation follows
console,logbook.archive	Documentation follows
console.notifications,send	Documentation follows
console.report.export	Documentation follows
console.search.query	Documentation follows
console.settings.all	Documentation follows
console.system.autoincrement
   */
}
