package net.vellity.service.worker.worker.dailyreport

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectYesterdayGlobalDailyLoginsAmount(private val context: Context = Context.ALL) : SelectQuery<Int> {
  override fun query(): String = if (context == Context.ALL) {
    "select count(*) as amount from usercache_users_logins where date(`date`) = date(now() - interval 1 day)"
  } else {
    "select count(*) as amount from usercache_users_logins where date(`date`) = date(now() - interval 1 day) and context = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    if (context != Context.ALL) {
      preparedStatement.setContext(1, context)
    }
  }

  override fun mapper(resultSet: ResultSet): Int = resultSet.getInt("amount")
}