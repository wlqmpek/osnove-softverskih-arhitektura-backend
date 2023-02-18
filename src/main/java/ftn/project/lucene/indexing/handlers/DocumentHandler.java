package ftn.project.lucene.indexing.handlers;


import ftn.project.models.Article;
import ftn.project.models.ArticleElastic;
import ftn.project.models.Description;

import java.io.File;

public abstract class DocumentHandler {
	/**
	 * Od prosledjene datoteke se konstruise Lucene Document
	 * 
	 * @param file
	 *            datoteka u kojoj se nalaze informacije
	 * @return Lucene Document
	 */
	public abstract ArticleElastic getIndexUnit(File file);
	public abstract String getText(File file);

}
