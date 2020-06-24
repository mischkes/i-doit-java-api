# i-doit-java-api
Java Library for i-doit JSON-RPC. See https://kb.i-doit.com/
##Todo
Was ist mit
* Search (ReadObjects mit Filter)
* Get von verschiedenen Typen --> geht nicht, mehrfaches Get nötig

##Guidelines
Libraries
* Jersey (REST)
* Lombok
* No CDI

API
* DTO based
* Batch Operations
* Auto-Batch (Upsert)

##Missing features

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
