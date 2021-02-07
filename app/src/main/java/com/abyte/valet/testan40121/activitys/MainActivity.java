package com.abyte.valet.testan40121.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.model.Content;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_NAME = "Msg";
    public static final String MSG_POS = "Position";
    public static final String MSG_ID_BACK_FRAGMENT = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.my_tool_bar);

        this.setSupportActionBar(toolbar);
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);

        ArrayList<Content> contents;

        contents = new ArrayList<>();

        contents.add(new Content(R.drawable.w,"Valet Byte", "Wifi jammer", (byte) 3));

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


        Bundle bundle = new Bundle();
        bundle.putSerializable(MSG_NAME, contents);
        NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))).navigate(R.id.projectsFragment2, bundle);

        navigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
                switch (item.getItemId()){
                    case R.id.projectsFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.projectsFragment2, bundle);
                        break;
                    case R.id.ideaFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.ideaFragment2, bundle);
                        break;
                    case R.id.articleFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.articleFragment2, bundle);
                        break;
                    case R.id.personalFragment2:
                        NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).navigate(R.id.personalFragment2);
                        break;

                }
                return true;
            }
        );

        
    }

}