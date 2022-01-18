package example

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2.concurrent.Queue

object Main extends IOApp {

  class Enqueuer(queue: Queue[IO, Int]) {
    def enqueue(i: Int): Unit = queue.enqueue1(i).unsafeRunAsync(_ => ())
  }
  def run(args: List[String]): IO[ExitCode] =
    for {
      queue <- Queue.unbounded[IO, Int]
      enq = new Enqueuer(queue)
      _ <- (1 to 10).toList.traverse_(i => IO(enq.enqueue(i)))
      _ <- queue.dequeue.map(println).take(10).compile.drain
    } yield ExitCode.Success
}
