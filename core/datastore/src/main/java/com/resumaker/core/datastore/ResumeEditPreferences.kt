package com.resumaker.core.datastore

import android.content.Context
import android.content.SharedPreferences

/**
 * 이력서 편집 화면 관련 로컬 설정.
 * Side-Effect 격리를 위해 ViewModel에서 주입받아 사용합니다.
 * (향후 DataStore로 전환 시 Flow 기반 API로 확장 가능)
 */
class ResumeEditPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun hasVisitedResumeEdit(): Boolean = prefs.getBoolean(KEY_VISITED_RESUME_EDIT, false)

    fun setVisitedResumeEdit(visited: Boolean) {
        prefs.edit().putBoolean(KEY_VISITED_RESUME_EDIT, visited).apply()
    }

    companion object {
        private const val PREFS_NAME = "resumaker_prefs"
        private const val KEY_VISITED_RESUME_EDIT = "has_visited_resume_edit"
    }
}
