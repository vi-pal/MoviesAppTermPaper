package com.example.data.repository

import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.data.db.AppItemDao
import com.example.data.util.TAG_FAVORITES_REPOSITORY
import com.example.domain.entities.AppItem
import com.example.domain.entities.Movie
import com.example.domain.entities.Person
import com.example.domain.usecase.IFavoritesRepository
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val movieDao: AppItemDao.MovieDao,
    private val personDao: AppItemDao.PersonDao
): IFavoritesRepository {

    val favoritesLiveData = MutableLiveData<MutableList<AppItem>>()
    val loadingLiveData = MutableLiveData<Boolean?>()

    override fun getFavorites(){
        val favorites = mutableListOf<AppItem>()
        favorites.addAll(movieDao.getFavoriteMovies())
        favorites.addAll(personDao.getFavoritePeople())
        favoritesLiveData.postValue(favorites)
    }

    override fun saveDocument() {
        val favorites = favoritesLiveData.value
        if (favorites != null && favorites.isNotEmpty()) {
            val pdfDoc = Document()
            val pdfDocUid = UUID.randomUUID()
            val pdfDocName = "FavoritesList #${pdfDocUid}"
            val path = "${Environment.getExternalStorageDirectory()}/$pdfDocName.pdf"

            try {
                PdfWriter.getInstance(pdfDoc, FileOutputStream(path))
                pdfDoc.open()

                pdfDoc.addAuthor("User")

                // fonts
                val headingFont = Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)
                val normalFont = Font(Font.FontFamily.HELVETICA, 14f)

                var nameParagraph = Paragraph()
                var infoParagraph = Paragraph()

                for (item in favorites) {
                    if (item is Movie) {
                        nameParagraph = Paragraph(item.title, headingFont)
                        val info = "Rating: ${item.rating}\nVote count: ${item.voteCount}"
                        infoParagraph = Paragraph(info, normalFont)
                    }
                    else if (item is Person) {
                        nameParagraph = Paragraph(item.name, headingFont)
                        var info = "Popularity: ${item.popularity}\nKnown for: "
                        for (movie in item.movies) {
                            info += movie.title + "; "
                        }
                        infoParagraph = Paragraph(info, normalFont)
                    }
                    pdfDoc.add(nameParagraph)
                    pdfDoc.add(infoParagraph)
                    pdfDoc.add(Paragraph("\n"))
                }
                pdfDoc.close()
                loadingLiveData.postValue(true) // success
            } catch (e: Exception) {
                Log.d(TAG_FAVORITES_REPOSITORY, e.stackTraceToString())
                loadingLiveData.postValue(false) // failure
            }
        } else {
            loadingLiveData.postValue(null) // list is empty
        }

    }
}