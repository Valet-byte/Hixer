package com.abyte.valet.testan40121.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abyte.valet.testan40121.CustomLayoutManager.CustomLayoutManager;
import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.activitys.MainActivity;
import com.abyte.valet.testan40121.adapters.ContentAdapter;
import com.abyte.valet.testan40121.model.Content;

import java.util.ArrayList;
import java.util.List;


public class ProjectsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomLayoutManager layoutManager;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    public ContentAdapter contentAdapter;
    private List<Content> contents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        contents = new ArrayList<>();

        contents.add(new Content(R.drawable.w,"Valet Byte", "Wifi jammer"));

        contents.get(0).addChapter("Приветствие", "Привет, это пробная статья в которой я хотел рассказать вам как сделать Wifi jammer, или Wifi глушилку, называйте как хотите." +
                "В последнее время большую популярность приобрели недорогие и миниатюрные платы с поддержкой программной платформы NodeMCU. Они построены на модуле ESP8266, который реализует работу с Wi-Fi по стандарту 802.11b/g/n на частоте 2,4 ГГц. Сейчас встречается два варианта подобных плат: с чипом CP2102 американской компании Silicon Labs или с китайским CH340");

        contents.get(0).addChapter("Предупреждение", "Все рассмотренные советы настоятельно рекомендуется использовать только в образовательных целях. Блокировка передачи данных и использование рассмотренных средств может преследоваться по закону.В репозитории на GitHub ты можешь выбрать версию для конкретной платы." );
        contents.get(0).addChapter("Инструкция", R.drawable.d,
                "https://github.com/SpacehuhnTech/esp8266_deauther/releases" +
                        " Файлы с расширением bin — это скомпилированные скетчи. Их нужно устанавливать на плату через специальный загрузчик. Но если захочешь, то в архивах с исходным кодом ты отыщешь библиотеки и скетчи, которые можно поставить через Arduino IDE.");
        contents.get(0).addChapter(" ", R.drawable.b, "Загрузка .bin\n" +
                "Если ты выбрал вариант с загрузкой бинарника, то для начала запускай программу NodeMCU Flasher. Скачать ее можно в репозитории NodeMCU. Устанавливаем драйверы для CP2102 либо для CH340. После этого подключаем плату к компьютеру, открываем программу NodeMCU Flasher, выбираем порт COM в диспетчере устройств в разделе «Порты (COM и LPT)». Теперь переходим во вкладку Config, жмем на шестеренку и выбираем скачанный файл .bin. После добавления файла в строке слева появится его путь. Переходи во вкладку Operation и нажимай на Flash — прошивка после этого будет загружена в плату.");

        contents.get(0).addChapter("Практика", "После включения плата создаст точку доступа. Подключайся к ней и заходи по адресу 192.168.4.1 или deauth.me. Ты попадешь в конфигуратор и увидишь предупреждение." +
                "В разделе конфигурации в строке LANG указываем ru для включения русского языка в веб-интерфейсе. Чтобы настройки вступили в силу, нужно нажать на «Сохранить» и перезагрузить устройство. Теперь оно готово к работе, можно начинать портить жизнь соседям." +
                "Если в разделе сканирования нажать Scan APs, то глушилка находит все точки доступа Wi-Fi. Выбираешь одну или несколько сетей, и можно переходить в раздел атак. Режим Deauth отключает все устройства от выбранной сети. Режим Beacon позволяет создавать одновременно до 60 точек доступа." );



        recyclerView = (RecyclerView) view.findViewById(R.id.rv_content);
        layoutManager = new CustomLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        contentAdapter = new ContentAdapter(getContext(), contents, this);
        recyclerView.setAdapter(contentAdapter);

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();

                contents.remove(pos);

                contentAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    public void updateAdapter(){
        contentAdapter.notifyDataSetChanged();
    }

    public void addContent(Content content){
        contents.add(content);
        updateAdapter();
    }
}