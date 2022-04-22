
商品搜索 mapping

PUT product

```json
{
  "mappings" : {
    "properties" : {
      "attrs" : {
        "type" : "nested",
        "properties" : {
          "attrId" : {
            "type" : "long"
          },
          "attrName" : {
            "type" : "keyword"
          },
          "standard" : {
            "type" : "keyword"
          },
          "standardName" : {
            "type" : "keyword"
          },
          "attrType" : {
             "type" : "integer"
          },
          "cycle" : {
              "type" : "integer"
           }
        }
      },
      "partyCode" : {
        "type" : "text",
        "fields" : {
          "keyword" : {
            "type" : "keyword",
            "ignore_above" : 256
          }
        }
      },
      "cycle" : {
        "type" : "integer"
      },
      "createTime" : {
        "type" : "date"
      },
      "searchType" : {
        "type" : "integer"
      },
      "mainImgUrl" : {
        "type" : "text",
        "fields" : {
          "keyword" : {
            "type" : "keyword",
            "ignore_above" : 256
          }
        }
      },
      "marketPriceFee" : {
        "type" : "long"
      },
      "priceFee" : {
        "type" : "long"
      },
      "saleNum" : {
        "type" : "integer"
      },
      "sellingPoint" : {
        "type" : "text",
        "analyzer" : "ik_max_word",
        "search_analyzer" : "ik_smart"
      },
      "shopId" : {
        "type" : "long"
      },
      "shopImg" : {
        "type" : "keyword",
        "index" : false,
        "doc_values" : false
      },
      "shopName" : {
        "type" : "text",
        "analyzer" : "ik_max_word",
        "search_analyzer" : "ik_smart"
      },
      "shopType" : {
        "type" : "integer"
      },
      "primaryCategoryId" : {
        "type" : "long"
      },
      "primaryCategoryName" : {
        "type" : "keyword"
      },
      "secondaryCategoryId" : {
        "type" : "long"
      },
      "secondaryCategoryName" : {
        "type" : "keyword"
      },
      "categoryId" : {
        "type" : "long"
      },
      "addrId" : {
        "type" : "long"
      },
      "categoryName" : {
        "type" : "keyword"
      },
      "spuId" : {
        "type" : "long"
      },
      "spuName" : {
        "type" : "text",
        "analyzer" : "ik_max_word",
        "search_analyzer" : "ik_smart"
      },
      "spuStatus" : {
        "type" : "integer"
      },
      "seq" : {
        "type" : "integer"
      }
    }
  }
}
```
