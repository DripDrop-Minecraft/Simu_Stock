package games.dripdrop.simustock.center

import games.dripdrop.simustock.bean.Company
import games.dripdrop.simustock.bean.Order
import games.dripdrop.simustock.database.SQLiteDatabaseManager
import games.dripdrop.simustock.interfaces.IExchange

internal class SystemExchange(sqLiteDatabaseManager: SQLiteDatabaseManager) : IExchange {

    override fun canTradeNow(): Boolean {
        TODO("Not yet implemented")
    }

    override fun completeOrders(vararg orders: Order) {
        TODO("Not yet implemented")
    }

    override fun cacheOrders(vararg orders: Order) {
        TODO("Not yet implemented")
    }

    override fun abandonOrders(vararg orderNumbers: String) {
        TODO("Not yet implemented")
    }

    override fun listCompany(vararg companies: Company) {
        TODO("Not yet implemented")
    }

    override fun delistCompany(vararg stockCodes: String) {
        TODO("Not yet implemented")
    }

    override fun saveTradeData() {
        TODO("Not yet implemented")
    }

    override fun announce(content: String) {
        TODO("Not yet implemented")
    }
}