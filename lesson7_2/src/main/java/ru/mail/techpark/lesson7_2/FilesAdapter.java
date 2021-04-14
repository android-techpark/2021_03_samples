package ru.mail.techpark.lesson7_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Адаптер для {@link RecyclerView}, отображающий список файлов в указанной директории.
 * Начинает обзор с корня внешнего хранилища. Поддерживает навигацию по директориям.
 * Для упрощения кода опущены проверки наличия (примонтированности) внешнего хранилища.
 */
class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    private final LayoutInflater layoutInflater;
    private File initialFile;

    private File[] files;
    private File currentFile;

    FilesAdapter(final Context context) {
        layoutInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        initialFile = Environment.getExternalStorageDirectory();
        setDirectory(initialFile);
    }

    private void setDirectory(final File file) {
        this.currentFile = file;
        this.files = file.listFiles();
        sortFiles(this.files);
        notifyDataSetChanged();
    }

    boolean goBack() {
        if (currentFile.equals(initialFile)) {
            return false;
        }
        File parent = currentFile.getParentFile();
        if (parent != null) {
            setDirectory(parent);
            return true;
        }
        return false;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileViewHolder(layoutInflater.inflate(R.layout.file_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        holder.bind(files[position]);
    }

    @Override
    public int getItemCount() {
        return files != null ? files.length : 0;
    }

    private static void sortFiles(final File[] files) {
        if (files == null) {
            return;
        }
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && !f2.isDirectory()) return -1;
                if (f2.isDirectory() && !f1.isDirectory()) return 1;
                return f1.getName().compareTo(f2.getName());
            }
        });
    }

    final class FileViewHolder extends RecyclerView.ViewHolder {
        private final TextView mFileName;

        FileViewHolder(View view) {
            super(view);
            mFileName = view.findViewById(R.id.filename);
        }

        @SuppressLint("SetTextI18n")
        void bind(final File file) {
            if (file.isDirectory()) {
                mFileName.setTypeface(null, Typeface.BOLD);
                mFileName.setText("[" + file.getName() + "]");
            } else {
                mFileName.setTypeface(null, Typeface.NORMAL);
                mFileName.setText(file.getName());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.isDirectory()) {
                        FilesAdapter.this.setDirectory(file);
                    }
                }
            });
        }
    }
}

