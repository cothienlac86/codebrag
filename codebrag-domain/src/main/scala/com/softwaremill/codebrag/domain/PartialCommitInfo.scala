package com.softwaremill.codebrag.domain

import org.bson.types.ObjectId
import java.util.Date
import org.joda.time.DateTime

case class PartialCommitInfo(id: ObjectId, sha: String, message: String, authorName: String, authorEmail: String, date: DateTime)