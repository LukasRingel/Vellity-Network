package net.vellity.service.punish.punishments

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface PunishmentController {
  @GetMapping("/punishments")
  fun getActivePunishments(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam(
      required = false,
      name = "details",
      defaultValue = "INCLUDE"
    ) details: PunishmentRequestDetails = PunishmentRequestDetails.INCLUDE
  ): ResponseEntity<List<Punishment>>

  @GetMapping("/punishments/id")
  fun getPunishmentWithId(
    @RequestParam("id") id: Int,
  ): ResponseEntity<Punishment>

  @GetMapping("/punishments/type")
  fun getActivePunishmentsOfType(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("type") type: PunishmentType,
    @RequestParam(
      required = false,
      name = "details",
      defaultValue = "INCLUDE"
    ) details: PunishmentRequestDetails = PunishmentRequestDetails.INCLUDE
  ): ResponseEntity<List<Punishment>>

  @PostMapping("/punishments")
  fun createPunishment(
    @RequestParam("context") context: Context,
    @RequestParam("type") type: PunishmentType,
    @RequestParam("player") player: UUID,
    @RequestParam("reason") reason: Int,
    @RequestParam("actor") actor: UUID
  ): ResponseEntity<Punishment>

  @DeleteMapping("/punishments")
  fun deletePunishment(
    @RequestParam("id") id: Int,
    @RequestParam("deletedBy") deletedBy: UUID,
    @RequestParam("reason") reason: String
  ): ResponseEntity<Unit>
}