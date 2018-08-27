package com.example.mipro.netschool.Diary;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mipro.netschool.Diary.Quest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.mipro.netschool.Diary.Quest.COMP_Q;
import static com.example.mipro.netschool.Diary.Quest.NEW_Q;
import static com.example.mipro.netschool.Diary.Quest.PROC_Q;
import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class ClientSocketHelper {

    public ClientSocketHelper() {

    }

    public ArrayList<Quest> getTasksAndMarks(Calendar week, int id) {
        ArrayList<Quest> result = new ArrayList<Quest>();

        for (int i = 0; i < lessons.length; i++) {
            result.add(new Quest(i, statuses[i], types[i], dates[i],
                    lessons[i], authors[i % 3], "{" + week.get(Calendar.DAY_OF_MONTH) + "} " + descriptions[i],
                    typess[i % 2], markes[i], grades[i], dows[i]));
        }

        return result;
    }

    public void getLessonDescription(Quest q) {
        if (statuses[q.getId()] == NEW_Q)
            statuses[q.getId()] = PROC_Q;
        q.addData(desc, q.getId() == 0 ? "" : q.getId() % 2 == 1 ? "http://www.mathprofi.ru/" : "https://vk.com/");
    }

    public boolean markAsDone(Quest q) {
        statuses[q.getId()] = COMP_Q;
        return true;
    }
    public boolean markAsUndone(Quest q) {
        statuses[q.getId()] = PROC_Q;
        return true;
    }

    // TODO: remove this

    private static String[] lessons = { "Английский язык", "Математика", "Информатика", "Русский язык",
            "Математика", "Математика", "Биология", "Информатика" };
    private static String[] descriptions = { "Слова", "ЕГЭ 18", "Паскаль", "Упражнение 228",
            "2 + 4 = ?", "х - 1 = 2", "Параграф 12 читать читать читать читать читать читать читать"
            + " (читать) читать (читать) (читать) (читать) (читать) (читать) (читать) (читать)"
            + " (читать) параграф новый (читать) (читать) (читать) (читать)", "Компилятор" };
    private static String[] dates = { "02.10.2017", "17.04.2018", "01.05.2018", "17.05.2018",
            "13.07.2018", "13.07.2018", "13.07.2018", "14.07.2018"};
    private static String[] authors = {"Мельников Владислав Валерьевич", "Валерьевич", "Мельников Валерьевич"};
    private static String[] typess = {"Контрольная домашняя работа", "Контрольная проверочная"};
    private static String[] grades = { "", "", "", "", "", "3", "5", "4" };
    private static String[] markes = { "", "", "", "", "", "3", "5", "4" };
    private static int[] statuses = { NEW_Q, NEW_Q, PROC_Q, PROC_Q,
            PROC_Q, COMP_Q, COMP_Q, COMP_Q };
    private static boolean[] types = { true, true, true, true,
            false, true, true, true };
    private static boolean[] dows = { true, true, false, true, true, false, false, true};

    private static final String desc = "По аналогии с размерностью увекторных пространств, каждая абелева группа имеетранг. Он определяется как минимальная размерность пространства над полем рациональных чисел, в которое вкладывается фактор группы по еёкручению.\n" +
            "\n" +
            "Свойства\n" +
            "•Конечнопорождённые абелевы группы изоморфны прямым суммамциклических групп.\n" +
            "\n" +
            "•Конечные абелевы группы изоморфны прямым суммам конечных циклических групп.\n" +
            "\n" +
            "•Любая абелева группа имеет естественную структуру модуля над кольцом целых чисел. Действительно,\n" +
            "\n" +
            "пусть —натуральное число, а— элемент коммутативной группыс операцией, обозначаемой +, тогдаможно определить как(раз) и.\n" +
            "\n" +
            "•Утверждения и теоремы, верные для абелевых групп (то есть модулей над кольцом главных идеалов ), зачастую могут быть обобщены на модули над произвольным кольцом главных идеалов. Типичным\n" +
            "\n" +
            "примером является классификация конечнопорожденных абелевых групп. http://195.19.40.181:3386/\n" +
            "\n" +
            "•Множество гомоморфизмов всех групповых гомоморфизмов извсамо является абелевой группой. Действительно, пусть— двагомоморфизма групп между абелевыми группами, тогда их сумма, заданная как, тоже является гомоморфизмом (это неверно, еслинекоммутативная группа).\n" +
            "\n" +
            "Конечные абелевы группы\n" +
            "Основополагающая теорема о структуре конечной абелевой группы утверждает, что любая конечная абелева группа может быть разложена в прямую сумму своих циклических подгрупп, порядки которых являются степенями простых чисел. Это следствие общейтеоремы о структуре конечнопорождённых абелевых групп для случая, когда группа не имеет элементов бесконечного порядка.изоморфнопрямой сумме итогда и только тогда, когдаивзаимно просты.\n" +
            "\n" +
            "https://yandex.ru/maps/?ll=-77.004021%2C21.283827&z=7 Следовательно, можно записать абелеву группу в форме прямой суммы\n" +
            "\n" +
            "двумя различными способами:\n" +
            "\n" +
            "•Где числа степени простых\n" +
            "\n" +
            "•Где делит, которое делит, и так далее до.\n" +
            "\n" +
            "Например, может быть разложено в прямую сумму двух циклических подгрупп порядков 3 и 5:. То же можно сказать про любую абелеву группу порядка пятнадцать, приходим к выводу, что все абелевы группы порядка 15 изоморфны.";

    // TODO end
}
