package com.fhss.shop.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View

import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 描述:
 *
 * @author zhangrq
 * 2017/3/30 11:42
 */
object BitmapUtils {
    private val TAG = BitmapUtils::class.java.canonicalName

    /**
     * @param path
     * @param sample
     * @return
     */
    fun decodeSampledBitmap(path: String, sample: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        // Calculate inSampleSize
        options.inSampleSize = sample

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    /**
     * 根据路径、获取图片的宽高
     *
     * @return width = int[0] ; height = int[1]
     */
    fun getWidthHeight(path: String): IntArray {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true// 这个参数设置为true才有效，
        BitmapFactory.decodeFile(path, options)// 这里的bitmap是个空
        val outWidth = options.outWidth
        val outHeight = options.outHeight
        return intArrayOf(outWidth, outHeight)
    }

    /**
     * Bitmap转byte[]
     */
    fun bitmapToByte(bmp: Bitmap): ByteArray {
        val bytes = bmp.byteCount
        val buf = ByteBuffer.allocate(bytes)
        bmp.copyPixelsToBuffer(buf)
        return buf.array()
    }

    /**
     * Uri转Bitmap
     */
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        try {
            return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * Bitmap转Uri
     */
    fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
        return Uri.parse(MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, null, null))
    }

    fun getPath(context: Context, uri: Uri): String {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val column_index = cursor!!
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    /**
     * 把Bitmap转Byte
     */
    fun Bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    fun getBitmapFormUri(ac: Activity, uri: Uri): Bitmap? {
        try {
            var input = ac.contentResolver.openInputStream(uri)
            val onlyBoundsOptions = BitmapFactory.Options()
            onlyBoundsOptions.inJustDecodeBounds = true
            onlyBoundsOptions.inDither = true//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
            input!!.close()
            val originalWidth = onlyBoundsOptions.outWidth
            val originalHeight = onlyBoundsOptions.outHeight
            if (originalWidth == -1 || originalHeight == -1)
                return null
            //图片分辨率以480x800为标准
            val hh = 800f//这里设置高度为800f
            val ww = 480f//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            var be = 1//be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (originalWidth / ww).toInt()
            } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (originalHeight / hh).toInt()
            }
            if (be <= 0)
                be = 1
            //比例压缩
            val bitmapOptions = BitmapFactory.Options()
            bitmapOptions.inSampleSize = be//设置缩放比例
            bitmapOptions.inDither = true//optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
            input = ac.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
            input!!.close()

            return compressImage(bitmap!!)//再进行质量压缩
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    fun compressImage(image: Bitmap): Bitmap? {

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset()//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10//每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray())//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    fun displayToGallery(context: Context, photoFile: File?) {
        if (photoFile != null && photoFile.exists()) {
            val photoPath = photoFile.absolutePath
            val photoName = photoFile.name

            try {
                val e = context.contentResolver
                MediaStore.Images.Media.insertImage(e, photoPath, photoName, null as String?)
            } catch (var5: FileNotFoundException) {
                var5.printStackTrace()
            }

            context.sendBroadcast(Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://$photoPath")))
        }
    }

    fun saveToFile(bitmap: Bitmap, folder: File): File? {
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        return saveToFile(bitmap, folder, fileName)
    }

    fun saveToFile(bitmap: Bitmap?, folder: File, fileName: String): File? {
        if (bitmap != null) {
            if (!folder.exists()) {
                folder.mkdir()
            }

            val file = File(folder, "$fileName.jpg")
            if (file.exists()) {
                file.delete()
            }

            try {
                file.createNewFile()
                val e = BufferedOutputStream(FileOutputStream(file))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, e)
                e.flush()
                e.close()
                return file
            } catch (var5: IOException) {
                var5.printStackTrace()
                return null
            }

        } else {
            return null
        }
    }

    fun getBitmapDegree(path: String): Int {
        var degree: Short = 0

        try {
            val e = ExifInterface(path)
            val orientation = e.getAttributeInt("Orientation", 1)
            when (orientation) {
                3 -> degree = 180
                6 -> degree = 90
                8 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree.toInt()
    }

    fun rotateBitmapByDegree(bitmap: Bitmap?, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val newBitmap = Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.width, bitmap.height, matrix, true)
        if (bitmap != null && !bitmap.isRecycled) {
            bitmap.recycle()
        }

        return newBitmap
    }

    fun decodeBitmapFromFile(imageFile: File?, requestWidth: Int, requestHeight: Int): Bitmap? {
        return if (imageFile != null) decodeBitmapFromFile(imageFile.absolutePath, requestWidth, requestHeight) else null
    }

    private fun decodeBitmapFromFile(imagePath: String, requestWidth: Int, requestHeight: Int): Bitmap? {
        if (TextUtils.isEmpty(imagePath)) {
            return null
        } else {
            Log.i(TAG, "requestWidth: $requestWidth")
            Log.i(TAG, "requestHeight: $requestHeight")
            if (requestWidth > 0 && requestHeight > 0) {
                val options1 = BitmapFactory.Options()
                options1.inJustDecodeBounds = true
                BitmapFactory.decodeFile(imagePath, options1)
                Log.i(TAG, "original height: " + options1.outHeight)
                Log.i(TAG, "original width: " + options1.outWidth)
                if (options1.outHeight == -1 || options1.outWidth == -1) {
                    try {
                        val e = ExifInterface(imagePath)
                        val height = e.getAttributeInt("ImageLength", 1)
                        val width = e.getAttributeInt("ImageWidth", 1)
                        Log.i(TAG, "exif height: $height")
                        Log.i(TAG, "exif width: $width")
                        options1.outWidth = width
                        options1.outHeight = height
                    } catch (var7: IOException) {
                        var7.printStackTrace()
                    }

                }

                options1.inSampleSize = calculateInSampleSize(options1, requestWidth, requestHeight)
                Log.i(TAG, "inSampleSize: " + options1.inSampleSize)
                options1.inJustDecodeBounds = false
                return BitmapFactory.decodeFile(imagePath, options1)
            } else {
                return BitmapFactory.decodeFile(imagePath)
            }
        }
    }

    fun decodeBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    fun decodeBitmapFromDescriptor(fileDescriptor: FileDescriptor, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2

            val halfWidth = width / 2
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2
            }

            var totalPixels = (width * height / inSampleSize).toLong()

            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toLong()
            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2
                totalPixels /= 2L
            }
        }

        return inSampleSize
    }

    fun copyViewToDrawable(view: View): Drawable {
        val width = view.width
        val height = view.height
        val bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bp)
        view.draw(canvas)
        canvas.save()
        return BitmapDrawable(bp)
    }

    fun screenshotsByView(view: View, file: File) {
        val width = view.width
        val height = view.height
        val bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bp)
        view.draw(canvas)
        canvas.save()
        try {
            bp.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))//file为保存文件
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }


    fun screenshotsByView(view: View): Bitmap {
        val width = view.width
        val height = view.height
        val bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bp)
        view.draw(canvas)
        canvas.save()
        return bp
    }

}
