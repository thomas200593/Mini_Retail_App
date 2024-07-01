package com.thomas200593.mini_retail_app.core.design_system.base_class

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail

interface BaseEntity{
    val seqId: Int
    val genId: String
    val auditTrail: AuditTrail
}