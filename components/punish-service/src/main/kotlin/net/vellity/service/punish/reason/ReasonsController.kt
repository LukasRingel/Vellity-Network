package net.vellity.service.punish.reason

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

interface ReasonsController {
  @GetMapping("/reasons")
  fun getReasons(
    @RequestParam("context") context: Context
  ): ResponseEntity<List<Reason>>

  @GetMapping("/reasons/id")
  fun getReasonById(
    @RequestParam("id") id: Int
  ): ResponseEntity<Reason>

  @PostMapping("/reasons")
  fun createReason(
    @RequestParam("context") context: Context,
    @RequestParam("name") name: String,
    @RequestParam("type") type: PunishmentType
  ): ResponseEntity<Reason>

  @DeleteMapping("/reasons")
  fun deleteReason(
    @RequestParam("id") id: Int
  ): ResponseEntity<Unit>

  @PostMapping("/reasons/duration")
  fun createOrUpdateDuration(
    @RequestParam("reasonId") reasonId: Int,
    @RequestParam("amount") amount: Int,
    @RequestParam("timeunit") timeunit: PunishmentTimeUnit,
    @RequestParam("multiplier") multiplier: Double
  ): ResponseEntity<ReasonDuration>
}