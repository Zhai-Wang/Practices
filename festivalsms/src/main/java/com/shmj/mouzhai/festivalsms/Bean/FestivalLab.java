package com.shmj.mouzhai.festivalsms.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mouzhai on 2016/11/16.
 */

public class FestivalLab {

    private static FestivalLab mInstance;

    private List<Festival> mFestivals = new ArrayList<>();
    private List<Msg> mMsgs = new ArrayList<>();

    public static FestivalLab getInstance() {
        if (mInstance == null) {
            synchronized (FestivalLab.class) {//防止多线程同时判断导致互斥
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }

    private FestivalLab() {
        //在构造方法里获取数据,显示具体有哪些节日
        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "元旦节"));
        mFestivals.add(new Festival(4, "春节"));
        mFestivals.add(new Festival(5, "圣诞节"));
        mFestivals.add(new Festival(6, "端午节"));
        mFestivals.add(new Festival(7, "七夕节"));
        mFestivals.add(new Festival(8, "儿童节"));

        //添加默认的短信内容
        mMsgs.add(new Msg(1, 1, "把握时令，创造时运。拼搏奋力，不必卖命，成龙成凤，须有自信。身体要紧，能拼能赢。且吟且行，欢度国庆。"));
        mMsgs.add(new Msg(2, 1, "飞舞的彩带是我的关怀，喧天的锣鼓是我的祝福。国庆佳节到了，祝你全家红红火火，和和美美，开开心心！"));
        mMsgs.add(new Msg(3, 1, "国庆国庆普天同庆，重阳重阳万事不难，在这个和平幸福的年代里，祝你们全家节日快乐！身体健康！"));
        mMsgs.add(new Msg(4, 1, "金秋十月，举国同庆；神州大地，繁花似锦；家和国盛，乐曲如潮，借着这伟大而美丽的日子送上我最诚挚的祝福：天天开心，事事顺意！"));
        mMsgs.add(new Msg(5, 1, "白云从不向天空承诺去留，却朝夕相处；星星从不向暗夜许诺光明，却尽力闪烁；我从不向你倾诉思念，却永远牵挂。国庆快乐！"));
        mMsgs.add(new Msg(6, 1, "国庆快乐，加班快乐！嘻嘻，加班的日子天天快乐，别在意节日怎么过！但我的祝福一直随你一起度过！愿你事业顺利，合家幸福！"));

        mMsgs.add(new Msg(1, 2, "月到中秋分外明，人到中秋共团圆，马上就是中秋节了，我祝你美梦好似月儿圆，美貌堪比月中仙，爱情要比月饼甜，家人一起共团圆。预祝中秋快乐！"));
        mMsgs.add(new Msg(2, 2, "中秋祝福：网缘！情缘！月圆！中秋夜语寄相思，花好月圆情难圆。带去问候和思恋，心想事成愿缘圆。"));
        mMsgs.add(new Msg(3, 2, "一片落叶而知秋，是深秋。一条短信而知秋，是立秋。一轮圆月而知秋，是中秋。朋友你要更坚强，添衣保暖不能忘，幸福快乐伴身旁！中秋快乐！"));
        mMsgs.add(new Msg(4, 2, "月到中秋分外明，秋色明媚月动听，花好月圆人欢庆，温暖康泰万家心。中秋佳节到了，祝你月来月开心，月来月幸福，月来月甜蜜，月来月美丽！"));
        mMsgs.add(new Msg(5, 2, "中秋节到了，情人聚聚增进感情，家人聚聚增进亲情，朋友聚聚增进友情，别忘短信经常句一句，祝大家众情俱增，中秋欢乐！"));
        mMsgs.add(new Msg(6, 2, "今秋又是月圆时，清风只影度中秋，思念佳人千里外，明月传情表我心。祝你中秋节快乐，人月两圆！"));
        mMsgs.add(new Msg(7, 2, "食月饼，赏婵娟，合家欢乐庆团圆；月皎洁，风翩跹，幸福快乐满心田；秋虫鸣，情缱绻，短信祝您中秋节快乐美满。"));
        mMsgs.add(new Msg(8, 2, "中秋祝福：七月七日鹊桥断，八月十五月儿圆.日暖秋凉菩提院，月儿无限月长圆。"));
        mMsgs.add(new Msg(9, 2, "月到中秋了，你我天各一方。岁月流失的都是美好，我会想你，你记得那句话吗？ 明月情依依，繁星语切切。"));

        mMsgs.add(new Msg(1, 3, "元旦快到了！我把收藏好的一月的喜庆，二月的春风，三月的花开，四月的快乐，五月的温馨，六月的纯真，七月的热情，八月的桂香，九月的骄阳，十月的收获，十一月的感恩，十二月的纯洁装进诚意的盒子里，用关怀的蝴蝶结装饰送给你，元旦快乐！"));
        mMsgs.add(new Msg(2, 3, "幸福是个“元”，永远不会断；烦恼统统完“旦”，罚它靠边站。这个元旦，给你“元”滋“元”味的祝愿：愿你好运“元元”而来，快乐“元元”不断！"));
        mMsgs.add(new Msg(3, 3, "转眼元旦到了，愿好运对你点头哈腰，快乐对你眉开眼笑；美好对你格外宠幸，吉祥对你另眼相待；平安对你趋之若鹜，健康对你关爱有加；幸福对你情有独钟，朋友则祝你元旦快乐，福寿安康！"));
        mMsgs.add(new Msg(4, 3, "滚。。。滚。。。滚。。。元旦快到了，我要把幸福快乐做成圆圈，让它滚向你，越滚越大……嘿嘿，有我这个朋友知足吧，也祝咱们在新的一年里友谊长存!"));
        mMsgs.add(new Msg(5, 3, "元旦快要到了，趁着还没有放假，欢庆晚会还没有开始，祝福短信还没有爆发，你的手机还有空间，我的手机还没欠费，发消息还没涨价，提前祝你元旦快乐！"));

        mMsgs.add(new Msg(1, 4, "手指一按，按去我的美好祝愿。手指一按，按出平安伴你到永远。手指一按，按去幸福生活很美满。手指一按，按去财富围你转。手指一按，短信发到你面前。祝新年快乐！"));
        mMsgs.add(new Msg(2, 4, "新年到了，愿你抱着平安，拥着健康，揣着幸福，携着快乐，搂着温馨，带着甜蜜，牵着财运，拽着吉祥，迈入新年，快乐度过每一天!"));
        mMsgs.add(new Msg(3, 4, "相逢是首悠扬的歌，相识是杯醇香的酒，相处是那南飞的雁，相知是根古老的藤，心静时会默默地祝福您，愿幸福与平安伴随着您一生。新年好心情！"));
        mMsgs.add(new Msg(4, 4, "新年到，齐欢笑，我的短信问你好；贴春联，蒸年糕，幸福生活好运罩；请门神，放鞭炮，福禄双全收红包；穿新衣，满街跑，祝羊年更美好！"));
        mMsgs.add(new Msg(5, 4, "炮竹迎春晓，祥瑞东屋绕；春联透墨香，福字临门倒；红灯光彩照，轻歌更曼妙；喜气挂眉梢，老少乐陶陶；举杯同欢庆，幸福无限好。恭祝新年，大吉大利！"));

        mMsgs.add(new Msg(1, 5, "雪花盛开着温暖，人间弥漫着蜜甜，烟筒袅袅出炊烟，火炉暖暖着慵懒，花树散发着芬芳，顽童讨要着果糖，驯鹿跑出来健康，圣诞洋溢着吉祥。祝：圣诞快乐!"));
        mMsgs.add(new Msg(2, 5, "我许下一个圣诞心愿，愿你的快乐如宇宙般浩瀚无边，愿你的生活如圣诞衣般红红火火，愿你的身体如圣诞小鹿般活力四射。这些愿望你能帮我实现吗?圣诞快乐!"));
        mMsgs.add(new Msg(3, 5, "圣诞雪舞着浪漫的气息，传递着节日的温馨;圣诞树挂着美丽的问候，传播着快乐的心情;圣诞节编着温情的短信，传送着真诚的祝福;圣诞节到，愿你开心相伴，吉祥如意!"));
        mMsgs.add(new Msg(4, 5, "烟花的璀璨，是快乐的开始;流星的闪亮，是愿望变现实;圣诞的钟声，是好运的来袭;短信的铃声，是我在祝福你，圣诞节到，祝你平安健康幸福!"));
        mMsgs.add(new Msg(5, 5, "圣诞节到，圣诞老人不是随便送礼物的，是因人而异，缺什么送什么，缺钱送钱，缺粮送粮，根据观察，圣诞老人决定送给你……智商!哈哈，祝圣诞节快乐!"));
        mMsgs.add(new Msg(6, 5, "雪花飘，圣诞到。铃铛响，驯鹿跑。快乐至，吉祥到。圣诞老人戴新帽，为送礼物不停跑。快快准备好微笑，把吉祥如意接收到。祝你圣诞快乐又美妙，幸福生活永围绕!"));
        mMsgs.add(new Msg(7, 5, "将笑容挂起，让快乐点缀节日的气氛;将好心情亮起，让幸福增添节日的喜悦，将短信发起，让文字送出节日的问候，圣诞节，愿你吉祥如意，幸福美满!"));
        mMsgs.add(new Msg(8, 5, "圣诞阳光为你而灿烂，圣诞雪花因你而浪漫，圣诞树上挂满我的思念，圣诞焰火辉映你的笑脸，圣诞钟声传递美好祝愿，圣诞短信遥祝你幸福平安!圣诞快乐!"));
        mMsgs.add(new Msg(9, 5, "它存在，你深深的脑海里，你的烟囱里，你的袜子里，你的手心里。啊，圣诞礼物，它存在，我的短信里，我的祝福里，我的欢笑里，圣诞快乐哦!"));
        mMsgs.add(new Msg(10, 5, "大雪纷飞时，你悄悄地走来;天寒地冻时，你呼呼地跑来。问我：“你冷不冷?”我说：“不冷。”“为什么?”“我的屋子里25度呢!”祝你圣诞快乐!"));

        mMsgs.add(new Msg(1, 6, "一声平常如纸的祝福，很真；一句平淡如水的问候，很轻；采一片清香的粽叶，包一颗香甜的粽子，装入真情的信息里，送给你：祝端午节快乐！"));
        mMsgs.add(new Msg(2, 6, "一声平淡的问候，很轻；一句平常的祝福，很真；采一片清香的粽叶，包一颗香甜的粽子，装入真情的信息里，送给你：祝端午节快乐！"));
        mMsgs.add(new Msg(3, 6, "一声问候送来温馨甜蜜，一条短信捎去我万般心意，一丝真诚胜过千两黄金，一丝温暖能抵万里寒霜，端午节快乐是我最大的心愿！"));
        mMsgs.add(new Msg(4, 6, "一层一层粽叶，包裹化不开的深情，一道一道丝线，缠绕剪不断的思念，一颗一颗米粒，飘洒最诱人的芳香，一口一口品尝，传递最深情的祝福，端午节快乐！"));
        mMsgs.add(new Msg(5, 6, "一把糯米、两颗红枣、三片粽叶、四根丝线、缠成五圈是端午；一个节日、两人祝福、三羊开泰、四季平安、聚成五福过端午！节日快乐！"));
        mMsgs.add(new Msg(6, 6, "一条平常的短信，一声平淡如水的问候；让我采一清香的粽叶，包一香甜的粽子，装进真情的信息里送给到你眼前：端午节祝你开心快乐。"));
        mMsgs.add(new Msg(7, 6, "人依旧，物依然，又到端午；想也好，忘也罢，只是问候；闲也行，忙也好，开心即可；今儿好，明更好，衷心祝愿：端午节快乐！"));
        mMsgs.add(new Msg(8, 6, "人的一生中总有一些朋友最珍惜，一年中总有一些曰子难忘记；从夏走到秋，由陌生转为熟悉，虽不能时时问候，却在特别的曰子，轻轻道声：朋友，端午节愉快！"));
        mMsgs.add(new Msg(9, 6, "人生只有一条路，一条路上多棵树。有钱的时候莫忘路，有难的时候靠靠树。幸福时候别迷路，休息时候浇浇树。端午节快乐！"));
        mMsgs.add(new Msg(10, 6, "人之祝福，有轻于鸿毛，有“粽”于泰山。但是千里送鹅毛，礼轻人意“粽”。端午节就要到了，提前送给你最真最“粽”的祝福，愿你快乐重于泰山！"));

        mMsgs.add(new Msg(1, 7, "我的心里有座山，你是山上唯一的客，你若快乐，它便苍松翠竹，漫山遍野桃红柳绿，你若悲伤，它便冰天雪地，视野所及满目疮痍。亲爱的七夕快乐！"));
        mMsgs.add(new Msg(2, 7, "对你的思念，化作满天眨眼的星星，日夜守护着你，形影不离！对你的牵挂，化作美丽的彩虹，连接你我，心有灵犀！清风徐徐，祝福无期，让真情和白云一起飘逸，让真爱和皎月共同涟漪！七夕快乐，快乐七夕！"));
        mMsgs.add(new Msg(3, 7, "鹊桥弯弯情谊浓，牛郎织女见面欢。星河璀璨空中闪，天上人间共缠绵。手牵手儿心相连，知心的话儿说不完。幸福生活绕身边，爱情滚滚似江河，滔滔不绝心中现。只愿此生永相伴，生生死死手相牵。愿你七夕快乐无限！"));
        mMsgs.add(new Msg(4, 7, "无需海誓山盟，无需甜言蜜语。爱就是心心相印、相濡以沫。爱就是风雨同舟、患难与共。七夕节，手牵手心连心我爱你天地鉴。"));
        mMsgs.add(new Msg(5, 7, "这是一条王母娘娘赐予的短信，凡是读到它的人将终生幸福，恋爱中的情侣将一生相爱，单身男女将遇美满爱情，祝愿全天下人七夕快乐。"));
        mMsgs.add(new Msg(6, 7, "相思苦，爱意浓，愿爱在你心中谱；银河美，鹊桥仙，愿爱在你身边绕；七夕到，喜鹊叫，让爱把你紧拥抱；情相依，爱无边，幸福伴你一生永不变！"));
        mMsgs.add(new Msg(7, 7, "七月七日七夕夜，牛郎织女来会面。天上人间情难断，恩恩爱爱心不变。哪怕王母银河变，也有鹊桥助神仙。你我情比磐石坚，坎坷虽有难断绝。此心可堪比日月，地久天长永不变。愿你七夕快乐笑开颜，你我相伴到海枯石烂！"));
        mMsgs.add(new Msg(8, 7, "七夕，几许相思泪；七夕，佳人心相随。七夕，天地唱团圆，七夕，爱情是最美，祝天下有情之人把手牵，无情之人早坠情网。七夕节快乐！"));
        mMsgs.add(new Msg(9, 7, "天上牛郎会织女，地上七夕情人节。喜鹊搭桥星满天，真情总被相思扰。眼前若有珍惜人，切莫错过悔终生。有情之人成眷属，短信祝福意真切。七夕快乐！"));
        mMsgs.add(new Msg(10, 7, "牛郎恋刘娘，刘娘念牛郎，牛郎念念恋刘娘，刘娘年年念牛郎，郎恋娘来娘恋郎，念娘恋娘念郎恋郎，念恋娘郎。七夕信息赶早发你，不绕晕你算我白忙！"));

        mMsgs.add(new Msg(1, 8, "别动，抢劫！这是抢劫！懂吗?快拿出你的忧愁，交出你的伤心，掏出你的烦恼，摘下你的哀伤，喏！换上这个，我送来的快乐！预祝六一儿童节快乐哦"));
        mMsgs.add(new Msg(2, 8, "童心是帆，祝福是船；快乐的风，吹着童心的帆，载着对你这长不大的朋友的祝福，漂向美丽的海湾，轻轻地问候你：六一节快乐！^0^"));
        mMsgs.add(new Msg(3, 8, "少一些烦恼，不论钞票多少，只要开心就好，累了就睡觉，醒了就微笑，童心到老，祝六一儿童节快乐！^_^"));
        mMsgs.add(new Msg(4, 8, "无知，并不可怕，可怕，则是无耻；贫穷，并不可怜，可怜，则是懒惰；幼稚，并不可笑，可笑，则是无知。六一儿童节，祝你开心快乐。"));
        mMsgs.add(new Msg(5, 8, "激情让岁月颓废，活力将年龄摧毁，童心让纯真永存，乐观让人生无悔，祝你儿童节快乐，天天快乐，永远都快乐！"));
        mMsgs.add(new Msg(6, 8, "有颗童心，一路欢欣；带着童真，美好共枕；满怀童趣，无限乐趣；与童年做伴，与快乐同行，祝你永远年轻，天天好心情！六一了，祝你儿童节快乐哈！"));
        mMsgs.add(new Msg(7, 8, "儿童节快乐！愿你永远拥有一颗童心，天天笑哈哈. 歌舞欢腾，六一儿童庆佳节，阳光灿烂，万千新花正宜人"));
    }

    /**
     * 获取节日名称
     *
     * @return 为了防止数据可能被修改，导致问题，此处返回一个 List
     */
    public List<Festival> getFestivals() {
        return new ArrayList<>(mFestivals);
    }

    /**
     * 根据 id 获取 Festival 对象
     *
     * @param id 需要查找的 id 号
     * @return Festival 对象；如果没有 id，返回 null
     */
    public Festival getFestivalById(int id) {
        for (Festival festival : mFestivals) {
            if (festival.getId() == id) {
                return festival;
            }
        }
        return null;
    }

    /**
     * 根据 id 获取 Msg 对象
     *
     * @param id 编号
     * @return Msg 对象；如果没有对应的 id，返回 null
     */
    public Msg getMsgById(int id) {
        for (Msg msg : mMsgs) {
            if (msg.getId() == id) {
                return msg;
            }
        }
        return null;
    }

    /**
     * 根据节日 id 获取短信内容，将之写入 List
     *
     * @param fesId 节日编号
     * @return List<Msg> 添加了 Msg 之后的 List
     */
    public List<Msg> getMsgsByFestivalId(int fesId) {
        List<Msg> msgs = new ArrayList<>();
        for (Msg msg : mMsgs) {
            if (msg.getFestivalId() == fesId) {
                msgs.add(msg);
            }
        }
        return msgs;
    }
}
