package net.vellity.service.punish.reason

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceReasonController(private val service: ReasonsService) : ReasonsController {
  override fun getReasons(context: Context): ResponseEntity<List<Reason>> {
    return ResponseEntity.ok(service.getReasons(context))
  }

  override fun getReasonById(id: Int): ResponseEntity<Reason> {
    return ResponseEntity.ok(service.getReasonById(id))
  }

  override fun createReason(context: Context, name: String, type: PunishmentType): ResponseEntity<Reason> {
    return ResponseEntity.ok(service.createReason(context, name, type))
  }

  override fun deleteReason(id: Int): ResponseEntity<Unit> {
    service.deleteReason(id)
    return ResponseEntity.ok().build()
  }

  override fun createOrUpdateDuration(
    reasonId: Int,
    amount: Int,
    timeunit: PunishmentTimeUnit,
    multiplier: Double
  ): ResponseEntity<ReasonDuration> {
    return ResponseEntity.ok(service.createOrUpdateDuration(reasonId, amount, timeunit, multiplier))
  }
}