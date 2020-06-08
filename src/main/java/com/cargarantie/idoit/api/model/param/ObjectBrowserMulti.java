package com.cargarantie.idoit.api.model.param;

/*
[
                {
                    "id": "10490",
                    "title": "Applikationsserver Enaio BlueLine",
                    "sysid": "SYSID_1587031426",
                    "type": "C__OBJTYPE__APPLICATION",
                    "type_title": "Application",
                    "prop_type": "browser_object",
                    "multiselection": 1
                },
                {
                    "id": "10529",
                    "title": "Yuuvis ElasticSearch",
                    "sysid": "SYSID_1587035951",
                    "type": "C__OBJTYPE__APPLICATION",
                    "type_title": "Application",
                    "prop_type": "browser_object",
                    "multiselection": 1
                },
                {
                    "id": "10547",
                    "title": "Yuuvis Metrics Manager",
                    "sysid": "SYSID_1587041340",
                    "type": "C__OBJTYPE__APPLICATION",
                    "type_title": "Application",
                    "prop_type": "browser_object",
                    "multiselection": 1
                },
                {
                    "id": "10596",
                    "title": "NB-IdoitUpsertObjectsServiceIT",
                    "sysid": "SYSID_IdoitUpsertObjectsServiceTest",
                    "type": "C__OBJTYPE__CLIENT",
                    "type_title": "Client",
                    "prop_type": "browser_object",
                    "multiselection": 1
                }
            ]
Out
--> Integer[]

/*
more ex
 */

import java.util.List;
//TODO
public class ObjectBrowserMulti {
  List<ObjectBrowser> data;

}
