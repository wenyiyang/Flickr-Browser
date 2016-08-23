package org.example.android.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create destination URI, crawl data, and parse data.
 */
public class GetFlickrJsonData extends GetRawData {

    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> photos;
    private Uri destinationUri;

    // Create destination URI and initialize photo list.
    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUri(searchCriteria,matchAll);
        photos = new ArrayList<Photo>();
    }

    // Activate the crawl and parse process.
    public void execute() {
        super.setRawUrl(destinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = " + destinationUri.toString());
        downloadJsonData.execute(destinationUri.toString());
    }

    // Create destination URI.
    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";
        destinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM,searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        return destinationUri != null;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    // Parse JSON data and store it in photo list.
    public void processResult() {
        if(getDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }
        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";
        try {
            JSONObject jsonData = new JSONObject(getData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for(int i=0; i<itemsArray.length(); i++) {
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);
                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);
                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                this.photos.add(photoObject);
            }
            for(Photo singlePhoto: photos) {
                Log.v(LOG_TAG, singlePhoto.toString());
            }

        } catch(JSONException jsone) {
            jsone.printStackTrace();
            Log.e(LOG_TAG,"Error processing Json data");
        }
    }

    /**
     * Download raw data with AsyncTask and parse data.
     */
    public class DownloadJsonData extends DownloadRawData {

        // Parse data and store it in photo list.
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();

        }

        // Crawl raw data and return it to onPostExecute.
        protected String doInBackground(String... params) {
            String[] par = { destinationUri.toString() };
            return super.doInBackground(par);
        }
    }
}