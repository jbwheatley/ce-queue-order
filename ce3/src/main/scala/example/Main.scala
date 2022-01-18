package example

import cats.effect.std.{Dispatcher, Queue}
import cats.effect.{IO, IOApp}
import cats.implicits._

object Main extends IOApp.Simple {

  class Enqueuer(queue: Queue[IO, Int]) {
    def enqueue(i: Int)(implicit dispatcher: Dispatcher[IO]): Unit = dispatcher.unsafeRunAndForget(queue.offer(i))
  }
  def run: IO[Unit] = {
    Dispatcher[IO].use { implicit d =>
      for {
        queue <- Queue.unbounded[IO, Int]
        enq = new Enqueuer(queue)
        _ <- (1 to 10).toList.traverse_(i => IO(enq.enqueue(i)))
        _ <- fs2.Stream.fromQueueUnterminated(queue).map(println).take(10).compile.drain
      } yield ()
    }
  }
}
