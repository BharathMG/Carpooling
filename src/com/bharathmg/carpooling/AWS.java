/*
 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.bharathmg.carpooling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;

import com.bharathmg.carpooling.R;
import com.parse.Parse;
import com.parse.ParseObject;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class AWS extends Activity {

	private AmazonS3Client s3Client = new AmazonS3Client(
			new BasicAWSCredentials("AKIAJ3LU7YZBC4SPO5YQ",
					"V5sSJwuqpzV30Wo/Tk9d+HYFuJSNhyahgwXnRDtz"));

	private Button selectPhoto = null;
	private Button showInBrowser = null;
	
	int myRandom;
	String notes;

	private Uri selectedImage;
	private static final int PHOTO_SELECTED = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
		
		setContentView(R.layout.activity_aws);
		Parse.initialize(this, "", "");
		Random rand = new Random();
		myRandom = rand.nextInt();
		notes = getIntent().getStringExtra("notes");
		selectPhoto = (Button) findViewById(R.id.select_photo_button);
		selectPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Start the image picker.
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, PHOTO_SELECTED);
			}
		});

		
	}

	// This method is automatically called by the image picker when an image is
	// selected.
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case PHOTO_SELECTED:
			if (resultCode == RESULT_OK) {

				selectedImage = imageReturnedIntent.getData();
				processKitKatFileData();
			}
		}
	}
	
	public void showall(View v){
		Intent i =  new Intent(this, AWSList.class);
		startActivity(i);
	}

	// Display an Alert message for an error or failure.
	protected void displayAlert(String title, String message) {

		AlertDialog.Builder confirm = new AlertDialog.Builder(this);
		confirm.setTitle(title);
		confirm.setMessage(message);

		confirm.setNegativeButton(
				AWS.this.getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});

		confirm.show().show();
	}

	protected void displayErrorAlert(String title, String message) {

		AlertDialog.Builder confirm = new AlertDialog.Builder(this);
		confirm.setTitle(title);
		confirm.setMessage(message);

		confirm.setNegativeButton(
				AWS.this.getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						AWS.this.finish();
					}
				});

		confirm.show().show();
	}

	private class S3PutObjectTask extends AsyncTask<File, Void, S3TaskResult> {

		ProgressDialog dialog;

		protected void onPreExecute() {
			dialog = new ProgressDialog(AWS.this);
			dialog.setMessage(AWS.this
					.getString(R.string.uploading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected S3TaskResult doInBackground(File... files) {

			if (files == null || files.length != 1) {
				return null;
			}

			S3TaskResult result = new S3TaskResult();

			// Put the image data into S3.
			try {
				
				s3Client.createBucket(myRandom + "");

				// Content type is determined by file extension.
				PutObjectRequest por = new PutObjectRequest(
						myRandom + "", "Picture", files[0]);
				s3Client.putObject(por);
				ResponseHeaderOverrides override = new ResponseHeaderOverrides();
				override.setContentType("image/jpeg");

				// Generate the presigned URL.

				// Added an hour's worth of milliseconds to the current time.
				Date expirationDate = new Date(
						System.currentTimeMillis() + 3600000);
				GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
						myRandom + "", "Picture");
				urlRequest.setExpiration(expirationDate);
				urlRequest.setResponseHeaders(override);

				URL url = s3Client.generatePresignedUrl(urlRequest);

				result.setUri(Uri.parse(url.toURI().toString()));
				ParseObject urlparse = new ParseObject("IMAGE");
				urlparse.put("image", url.toURI().toString());
				urlparse.put("notes", notes);
				urlparse.save();
				//s3Client.getObject(new GetObjectRequest());
			} catch (Exception exception) {

				result.setErrorMessage(exception.getMessage());
			}

			return result;
		}

		protected void onPostExecute(S3TaskResult result) {

			dialog.dismiss();

			if (result.getErrorMessage() != null) {

				displayErrorAlert(
						AWS.this
								.getString(R.string.upload_failure_title),
						result.getErrorMessage());
			}
		}
	}

	private class S3GeneratePresignedUrlTask extends
			AsyncTask<Void, Void, S3TaskResult> {
		
		protected S3TaskResult doInBackground(Void... voids) {

			S3TaskResult result = new S3TaskResult();

			try {
				// Ensure that the image will be treated as such.
				ResponseHeaderOverrides override = new ResponseHeaderOverrides();
				override.setContentType("image/jpeg");

				// Generate the presigned URL.

				// Added an hour's worth of milliseconds to the current time.
				Date expirationDate = new Date(
						System.currentTimeMillis() + 3600000);
				GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
						myRandom + "", "Picture");
				urlRequest.setExpiration(expirationDate);
				urlRequest.setResponseHeaders(override);

				URL url = s3Client.generatePresignedUrl(urlRequest);

				result.setUri(Uri.parse(url.toURI().toString()));

			} catch (Exception exception) {

				result.setErrorMessage(exception.getMessage());
			}

			return result;
		}

		protected void onPostExecute(S3TaskResult result) {
			
			if (result.getErrorMessage() != null) {

				displayErrorAlert(
						AWS.this
								.getString(R.string.browser_failure_title),
						result.getErrorMessage());
			} else if (result.getUri() != null) {

				// Display in Browser.
				startActivity(new Intent(Intent.ACTION_VIEW, result.getUri()));
			}
		}
	}

	private class S3TaskResult {
		String errorMessage = null;
		Uri uri = null;

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public Uri getUri() {
			return uri;
		}

		public void setUri(Uri uri) {
			this.uri = uri;
		}
	}
	/*
	 * Helper methods for getting absolute path from a selected file in KitKat
	 * versions Refer :: http://stackoverflow.com/a/20559175 ,
	 * https://github.com/iPaulPro/aFileChooser
	 */

	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
   private String getPath(final Context context, final Uri uri) {
       // DocumentProvider
       if (DocumentsContract.isDocumentUri(context, uri)) {
           // ExternalStorageProvider
           if (isExternalStorageDocument(uri)) {
               final String docId = DocumentsContract.getDocumentId(uri);
               final String[] split = docId.split(":");
               final String type = split[0];

               if ("primary".equalsIgnoreCase(type)) {
                   return Environment.getExternalStorageDirectory()  + "/" +  split[1];
               }
           }

           // DownloadsProvider
           else if (isDownloadsDocument(uri)) {

               final String id = DocumentsContract.getDocumentId(uri);
               final Uri contentUri = ContentUris.withAppendedId(
                       Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

               return getDataColumn(context, contentUri, null, null);
           }

           // MediaProvider
           else if (isMediaDocument(uri)) {
               final String docId = DocumentsContract.getDocumentId(uri);
               final String[] split = docId.split(":");
               final String type = split[0];

               Uri contentUri = null;
               if ("image".equals(type)) {
                   contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
               } else if ("video".equals(type)) {
                   contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
               } else if ("audio".equals(type)) {
                   contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
               }

               final String selection = "_id=?";
               final String[] selectionArgs = new String[] {
                       split[1]
               };

               return getDataColumn(context, contentUri, selection, selectionArgs);
           }
       }
       // MediaStore (and general)
       else if ("content".equalsIgnoreCase(uri.getScheme())) {
           return getDataColumn(context, uri, null, null);
       }
       // File
       else if ("file".equalsIgnoreCase(uri.getScheme())) {
           return uri.getPath();
       }

       return null;
   }

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	private boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * Gets the extension of a file name, like ".png" or ".jpg".
	 * 
	 * @param uri
	 * @return Extension including the dot("."); "" if there is no extension;
	 *         null if uri was null.
	 */
	private String getExtension(String uri) {
		if (uri == null) {
			return null;
		}

		int dot = uri.lastIndexOf(".");
		if (dot >= 0) {
			return uri.substring(dot);
		} else {
			// No extension.
			return "";
		}
	}

	/*
	 * Returns Thumbnail of given uri. This method is needed for preventing
	 * Memory leaks if image size is big. Refer
	 * http://stackoverflow.com/a/6228188 - Bharath
	 */
	private Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
		InputStream input = getContentResolver().openInputStream(uri);

		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
			return null;

		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
				: onlyBoundsOptions.outWidth;

		double ratio = (originalSize > 64) ? (originalSize / 64) : 1.0;

		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		input =  getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();
		return bitmap;
	}

	private int getPowerOfTwoForSampleRatio(double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0)
			return 1;
		else
			return k;
	}

	private void processKitKatFileData() {
		String absolute_path = getPath(this, selectedImage);
		File latest_file = new File(absolute_path);
		new S3PutObjectTask().execute(latest_file);
	}


}
