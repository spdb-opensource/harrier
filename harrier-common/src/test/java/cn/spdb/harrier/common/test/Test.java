package cn.spdb.harrier.common.test;

public class Test {

	private class TestThread extends Thread {

		@Override
		public void run() {
			while (!isInterrupted()) {

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("1" + isInterrupted());
			}
			System.out.println("end");

		}

	}

	@org.junit.Test
	public void test() throws InterruptedException {
		TestThread testThread = new TestThread();

		testThread.start();
		Thread.sleep(1000 * 2);
		testThread.interrupt();

		Thread.sleep(1000 * 10);
		System.out.println(testThread.isAlive());
	}

//	@org.junit.Test
//	public void test1() {
//
//		try {
//			URL url = new URL(urlStr);
//			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//			conn.setConnectTimeout(3 * 1000);
//			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			try (InputStream inputStream = conn.getInputStream();
//					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
//				while ((len = inputStream.read(buffer)) != -1) {
//					outputStream.write(buffer, 0, len);
//				}
//				outputStream.flush();
//				buffer = outputStream.toByteArray();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			File file = new File(filePath);
//			if (!file.getParentFile().exists()) {
//				file.getParentFile().mkdirs();
//			}
//			try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {
//				fileOutputStream.write(buffer);
//				fileOutputStream.flush();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//
//	}
}
