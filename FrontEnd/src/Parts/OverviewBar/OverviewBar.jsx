import styles from './OverviewBar.module.css'
import grass from './Grass.png'
import TaskPosting from "./TaskPosting/TaskPosting.jsx";

export default function OverviewBar() {
    return (
        <>
            <div className={styles.overviewBar}>
                <img src={grass} alt="Grass" />
                <TaskPosting />
                <TaskPosting />
                <TaskPosting />
            </div>
        </>
    )
}