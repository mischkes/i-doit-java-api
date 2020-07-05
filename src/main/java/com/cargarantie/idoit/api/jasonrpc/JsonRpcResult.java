package com.cargarantie.idoit.api.jasonrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/*
Example:
{
    "id": 1,
    "jsonrpc": "2.0",
    "result": [
        {
            "id": "7",
            "objID": "169",
            "primary_hostaddress": {
                "id": "176",
                "type": "C__OBJTYPE__LAYER3_NET",
                "title": "CG Network",
                "sysid": "SYSID_1581596506",
                "ref_id": "6",
                "ref_title": "",
                "ref_type": "C__CATS__NET_IP_ADDRESSES"
            },
            "primary_hostname": "",
            "net_type": {
                "id": "1",
                "title": "IPv4 (Internet Protocol v4)",
                "const": "C__CATS_NET_TYPE__IPV4",
                "title_lang": "IPv4 (Internet Protocol v4)"
            },
            "primary": {
                "value": "1",
                "title": "Yes"
            },
            "active": {
                "value": "1",
                "title": "Yes"
            },
            "net": {
                "id": "176",
                "title": "CG Network",
                "sysid": "SYSID_1581596506",
                "type": "C__OBJTYPE__LAYER3_NET",
                "type_title": "Layer 3-Net"
            },
            "zone": null,
            "ipv4_assignment": {
                "id": "1",
                "title": "DHCP",
                "const": "C__CATP__IP__ASSIGN__DHCP",
                "title_lang": "LC__CATP__IP__ASSIGN__DHCP"
            },
            "ipv4_address": {
                "id": "176",
                "type": "C__OBJTYPE__LAYER3_NET",
                "title": "CG Network",
                "sysid": "SYSID_1581596506",
                "ref_id": "6",
                "ref_title": "",
                "ref_type": "C__CATS__NET_IP_ADDRESSES"
            },
            "ipv6_assignment": {
                "id": "1",
                "title": "DHCPv6",
                "const": "C__CMDB__CATG__IP__DHCPV6",
                "title_lang": "LC__CMDB__CATG__IP__DHCPV6"
            },
            "ipv6_scope": {
                "id": "1",
                "title": "Global Unicast",
                "const": "C__CMDB__CATG__IP__GLOBAL_UNICAST",
                "title_lang": "LC__CMDB__CATG__IP__GLOBAL_UNICAST"
            },
            "hostaddress": {
                "id": "176",
                "type": "C__OBJTYPE__LAYER3_NET",
                "title": "CG Network",
                "sysid": "SYSID_1581596506",
                "ref_id": "6",
                "ref_title": "",
                "ref_type": "C__CATS__NET_IP_ADDRESSES"
            },
            "hostname": "",
            "domain": "",
            "dns_server": null,
            "dns_server_address": "7",
            "dns_domain": null,
            "use_standard_gateway": {
                "value": 0,
                "title": "No"
            },
            "assigned_port": null,
            "assigned_logical_port": null,
            "all_ips": "6",
            "primary_fqdn": [],
            "aliases": [],
            "description": ""
        }
    ]
}


Another Example:
{
    "id": 1,
    "jsonrpc": "2.0",
    "result": [
        {
            "documentId": "57",
            "key": "Client > Client Description",
            "value": "NB-Sebastian Mischke: Windows 10 Professional",
            "type": "cmdb",
            "link": "/?objID=57&catgID=53&cateID=1&customID=5&highlight=Windows",
            "score": "0",
            "status": "Normal"
        },
        {
            "documentId": "284",
            "key": "Server > Server Description",
            "value": "CGTSM: Microsoft Windows Server 2012 R2 Standard",
            "type": "cmdb",
            "link": "/?objID=284&catgID=53&cateID=1&customID=7&highlight=Windows",
            "score": "0",
            "status": "Normal"
        }
      ]
}


Another example "method": "cmdb.object.create":
{
    "id": 1,
    "jsonrpc": "2.0",
    "result": {
        "id": 5241,
        "message": "Object was successfully created",
        "success": true
    }
}

Another example "method": "cmdb.object.update":
{
    "id": 1,
    "jsonrpc": "2.0",
    "result": {
        "success": true,
        "message": "Category entry successfully saved. [This method is deprecated and will be removed in a feature release. Use 'cmdb.category.save' instead.]"
    }
}

Another example "method": "cmdb.object.delete":
{
    "id": 1,
    "jsonrpc": "2.0",
    "result": {
        "success": true,
        "message": "Category entry '46' successfully deleted"
    }
}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonRpcResult {
  private Integer id;
  private List<Object> result;
}
