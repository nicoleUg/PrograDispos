package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toEntity
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

class DollarLocalDataSource(
    private val dao: IDollarDao
) {

    suspend fun getList(): List<DollarModel> {
        return dao.getList().map {
            it.toModel()
        }
    }


    suspend fun getLatestDollar(): DollarModel? {
        return dao.getLatestDollar()?.toModel()
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun insertDollars(list: List<DollarModel>) {
        val dollarEntities = list.map { it.toEntity() }
        dao.insertDollars(dollarEntities)
    }

    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())
    }
}