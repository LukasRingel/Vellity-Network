package net.vellity.service.access.group.exception

import net.vellity.utils.context.Context

class GroupAlreadyExistsException(context: Context, name: String) :
  RuntimeException("Group $name already exists for context $context")