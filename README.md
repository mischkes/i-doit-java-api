[![Build status](https://travis-ci.com/mischkes/i-doit-java-api.svg?branch=travis-integration)](https://travis-ci.com/github/mischkes/i-doit-java-api)
 [![codecov.io Code Coverage](https://img.shields.io/codecov/c/github/dwyl/hapi-auth-jwt2.svg?maxAge=2592000)](https://codecov.io/github/dwyl/hapi-auth-jwt2?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/dwyl/hapi-auth-jwt2/badge.svg?targetFile=package.json)](https://snyk.io/test/github/dwyl/hapi-auth-jwt2?targetFile=package.json)
# i-doit-java-api
Java Library for i-doit JSON-RPC. The REST API is described at https://kb.i-doit.com/display/en/Methods


## Features
* All requests, responses, categories and objects are modelled as type-safe DTOs
* Supports batch operations
* Supports easy modification of full objects / list of objects

##Missing features
* Multi-Categories
* Dialog management
* Report management
* Deserializing to int and ObjectId fields, analog to IdoitStringDeserializer
* Language support

####Parameter type "DialogList"
Unsupported.

Sample Data

    {
      "id": "1",
      "title": "Net ip address",
      "hostname": "hostname.local",
      "obj_id": "10",
      "type": "C__CATG__IP"
    }
Sample Data 2

    {
      "id": "1",
      "title": "Port 1",
      "type": "C__CATG__NETWORK_PORT"
    }
    
####Parameter type "Multiselect"
No write support.

Sample Data

    [
      {
        "id": "4",
        "title": "tag1"
      },
      {
        "id": "5",
        "title": "tag2"
      }
    ]

####Parameter type "ObjectBrowser" - custom variants

Only supported as standard variant.

Sample Data

    {
      "id": "100",
      "title": "CMDB Object #100",
      "sysid": "SYSID_1280838789",
      "type": "C__OBJTYPE__SERVER",
      "type_title": "Server",
      //below seems proprietary
      "logPortId": "1",
      "logPortTitle": "Logical port 1"
    }
    
    {
      "id": "100",
      "title": "Cmdb object 1",
      "cable_id": "50",
      "sysid": "SYSID_1280838789",
      "type": "C__OBJTYPE__SERVER",
      "name": "Connector 1",
      "assigned_category": "@todo",
      "connector_type": "1",
      "con_type": "RJ-45"
    }
    
####Parameter type "MultiObjectBrowser"

Unsupported.

Sample Data

    [
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
    
####Custom category DTO cleaning
Custom categories currently have empty parameters, that are only filled with meta information.
Standard categories do not have that. As a result, java object parameters are parsed as empty,
not as null. This discrepancy should be fixed by removing parameters from json, that contain
only meta information.

    {
      "id": "821",
      "objID": "11457",
      "f_popup_c_1581595935188": {
        "identifier": "Form"
      },
      "f_popup_c_1581421490617": {
        "identifier": "OS"
      },
      "f_text_c_1581421491453": null,
      "f_popup_c_1581421518089": {
        "identifier": "Manufacturer"
      },
      "f_popup_c_1581421518780": {
        "identifier": "Model_Name"
      },
      "f_text_c_1581421862822": null,
      "f_popup_c_1581421868467": {
        "id": "51",
        "title": "FAAST",
        "const": null,
        "title_lang": "FAAST",
        "identifier": "CPU"
      },
      "f_popup_c_1581421880679": {
        "identifier": "RAM"
      },
      "f_popup_c_1581595990260": {
        "identifier": "HD_Type"
      },
      "f_text_c_1581595997348": null,
      "description": ""
    }
