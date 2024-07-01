package com.thomas200593.mini_retail_app.features.business.dao

import androidx.room.Dao
import javax.inject.Inject

@Dao
interface SupplierDao

class SupplierDaoImpl @Inject constructor(): SupplierDao