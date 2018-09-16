package cn.rivamed.achive;



    public class Etv002DataProtocol
    {
        public final static byte BEGIN_FLAG = (byte)0xB0;

        //设备自身通道
        public final byte CHAN_DEVICESELF = (byte)0x00;
        //IO 模块通道,Mensuo通道
        public final byte CHAN_IO = (byte)0x01;
        //串口1通道，高频读卡器
        public final byte CHAN_SER1_HFREADER = (byte)0x02;
        //串口2通道，指纹仪
        public final byte CHAN_SER2_FINGER = (byte)0x03;



        //成功状态
        public final byte SUCCESS_CODE = (byte)0x01;
        //锁已经开启
        public final byte LOCK_ALREADY_OPEN = (byte)0x02;
        //锁状态: 关闭
        public final byte LOCK_CLOSED = (byte)0x00;
        //锁状态: 开启
        public final byte LOCK_OPENED = (byte)0x01;
        //错误码表
        public final byte ERROR_SUCCESS = 0X10;

        public final static byte CMD_IO_OUTPUT_1_OPEN = 0X70;
        public final static byte CMD_IO_OUTPUT_1_CLOSE = 0X71;

        public final static byte CMD_IO_OUTPUT_2_OPEN = 0X73;
        public final static byte CMD_IO_OUTPUT_2_CLOSE = 0X74;

        public final static byte CMD_IO_OUTPUT_1_READ = 0X72;
        public final static byte CMD_IO_OUTPUT_2_READ = 0X75;

        public final static byte CMD_IO_INPUT_1_OPEN = (byte) 0X80;
        public final  static byte CMD_IO_INPUT_1_CLOSE = (byte) 0X81;

        public final static byte CMD_IO_INPUT_2_OPEN = (byte) 0X83;
        public final static byte CMD_IO_INPUT_2_CLOSE = (byte) 0X84;

        public final static byte CMD_IO_INPUT_1_READ = (byte) 0X82;
        public final static byte CMD_IO_INPUT_2_READ = (byte) 0X85;




        public static byte[] PieceCommond(byte[] buf)//将传进来的块指令重新重组
        {
//        	将传进来的数组加上4位
        	
            byte[] picecBuf = new byte[buf.length + 4]; //前三 后1
            
            int len1 = (buf.length + 1) / 256;
            int len2 = (buf.length + 1) % 256;
            
            picecBuf[0] = Etv002DataProtocol.BEGIN_FLAG;
            picecBuf[1] = (byte)(len1 & 0xff);
            picecBuf[2] = (byte)(len2 & 0xff);
            System.arraycopy(buf, 0, picecBuf, 3, buf.length);//将传进来的字节从第复制到自己定义的数组当中，length为需要复制的长度
            picecBuf[picecBuf.length - 1] = (byte)CheckSum(picecBuf, 0, picecBuf.length - 1);
            return picecBuf;
        }

        public static int CheckSum(byte[] buf, int offset, int len)
        {
            int i = 0, uSum = 0;
            for (i = 0; i < len; i++)
            {
                int v = buf[i + offset];
                uSum += 0xff & v;
            }
            uSum = (~(uSum & 0xff)) + 1;//按位取反
            return (uSum) & 0xff;
        }

        
        //检测指令是否有错误
        public static byte[] FingerCheckSum(byte[] buf, int offset, int len)
        {
            int uSum = 0;
            for (int i = 0; i < len; i++)
            {
                int v = buf[i + offset];
                uSum += 0xff & v;
            }
            byte[] ret = new byte[] { 0x00, 0x00 };
            ret[0] = (byte)((uSum / 256) & 0xff);
            ret[1] = (byte)((uSum % 256) & 0xff);
            return ret;
        }
    }

