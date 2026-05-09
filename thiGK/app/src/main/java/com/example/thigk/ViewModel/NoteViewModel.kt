package com.example.thigk.ViewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.thigk.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class NoteViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://thigkapp-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val myRef = database.getReference("notes")

    val noteList = mutableStateListOf<Note>()

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()


                for (data in snapshot.children) {
                    val note = data.getValue(Note::class.java)
                    note?.let { noteList.add(it) }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun saveNote(
        noteId: String?,
        title: String,
        desc: String,
        fileUri: Uri?,
        existingUrl: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val id = noteId ?: myRef.push().key ?: return
        val currentUid = auth.currentUser?.uid

        if (fileUri != null) {
            MediaManager.get().upload(fileUri)
                .unsigned("tnguyet_preset")
                .callback(object : UploadCallback {
                    override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                        val downloadUrl = resultData?.get("secure_url").toString()
                        val note = Note(id, title, desc, downloadUrl, currentUid)
                        myRef.child(id).setValue(note).addOnSuccessListener { onSuccess() }
                    }
                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        onError(error?.description ?: "Lỗi tải ảnh")
                    }
                    override fun onStart(requestId: String?) {}
                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                }).dispatch()
        } else {
            val note = Note(id, title, desc, existingUrl ?: "", currentUid)
            myRef.child(id).setValue(note).addOnSuccessListener { onSuccess() }
        }
    }

    fun deleteNote(id: String) {
        myRef.child(id).removeValue()
    }

    fun logout() {
        auth.signOut()
    }
}